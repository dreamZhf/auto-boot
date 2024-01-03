package com.auto.boot.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 核心代码为其IdWorker这个类实现，其原理结构如下，我分别用一个0表示一位，用—分割开部分的作用：
 * 1||0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * 在上面的字符串中，第一位为未使用（实际上也可作为long的符号位），接下来的41位为毫秒级时间，
 * 然后5位datacenter标识位，5位机器ID（并不算标识符，实际是为线程标识），
 * 然后12位该毫秒内的当前毫秒内的计数，加起来刚好64位，为一个Long型。
 * 这样的好处是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和机器ID作区分），
 * 并且效率较高，经测试，snowflake每秒能够产生26万ID左右，完全满足需要。
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 *
 * 此类无法在实例伸缩时自动修改workerId
 *
 * @author zhaohaifan
 */
@Slf4j
public class Snowflasker {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）开始时间 （2021-11-23）
     */
    private final static long twepoch = 1637650772994L;

    /**
     * 数据中心标识位数，增加到7位，最大127个数据中心
     */
    private final static long datacenterIdBits = 7L;

    /**
     * 机器标识位数，单个服务实例最大31个
     */
    private final static long workerIdBits = 5L;

    /**
     * 毫秒内自增位,控制QPS，最大1023
     */
    private final static long sequenceBits = 10L;

    /**
     * 支持的最大数据标识i
     */
    private final static long maxDatacenterId = ~(-1L << datacenterIdBits);

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final static long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 生成序列的掩码
     */
    private final static long sequenceMask = ~(-1L << sequenceBits);

    /**
     * 机器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;

    /**
     * 数据中心ID左移17位 （12+5）
     */
    private final static long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间毫秒左移22位  （5+5+12）
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 上次生产id时间戳
     */
    private static long lastTimestamp = -1L;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private final long datacenterId;


    public Snowflasker() {
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getWorkerId(datacenterId, maxWorkerId);
        log.info("[初始化雪花算法] : workerId：{}  datacenterId：{}", this.datacenterId, this.workerId);
    }

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public Snowflasker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public long getWorkerId() {
        return this.workerId;
    }

    public long getDatacenterId() {
        return this.datacenterId;
    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //初始值为随机数，避免全是0，失去离散性
            sequence = ThreadLocalRandom.current().nextInt(10);
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID

        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static long getMaxWorkerId() {
        return maxWorkerId;
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     */
    protected static long getWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
//            InetAddress ip = InetAddress.getLocalHost();
            InetAddress ip = InetAddress.getByName(getHostIp());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtils.nextLong(0, 31);
        }
        return id;
    }


    private static String getHostIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("获取ip失败");
        }
        return null;
    }
}
