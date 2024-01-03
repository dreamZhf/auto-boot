package com.auto.boot.common.utils;

import com.github.yitter.idgen.YitIdHelper;

/**
 * 雪花id生成工具类
 *
 * @author zhaohaifan
 */
public class IdUtil {

    /**
     * 雪花算法
     */
    private static Snowflasker ID_WORKER = null;

    /**
     * 获取雪花算法对象
     *
     * @return
     */
    private synchronized static Snowflasker getIdWorker(){
        if (ID_WORKER == null){
            ID_WORKER = new Snowflasker();
        }
        return ID_WORKER;
    }

    /**
     * 通过雪花算法获取ID
     *
     * @return
     */
    public static Long nextId(){
        return YitIdHelper.nextId();
    }

    /**
     * 通过雪花算法获取ID
     *
     * @return
     */
    public static String nextStrId(){
        return String.valueOf(nextId());
    }

    public static Long snowNextId(){
        return getIdWorker().nextId();
    }
}
