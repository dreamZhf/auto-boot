package com.auto.boot.starter.common.properties;

import com.auto.boot.starter.common.utils.PatternMatchUtil;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 配置类
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = AutoProperties.PREFIX)
public class AutoProperties {

    protected static final String PREFIX = "auto-boot";

    /**
     * 日志配置
     */
    private LogProperties log;

    /**
     * traceId 开关
     */
    private boolean traceIdEnable = true;

    public AutoProperties() {
        this.log = new LogProperties();
    }

    /**
     * 日志配置
     */
    @Getter
    @Setter
    @ToString
    public static class LogProperties {

        /**
         * 开关
         */
        private boolean enable = true;

        /**
         * 是否输出 header 日志 true: 是 false: 否
         */
        private boolean header = true;

        /**
         * 忽略某些 header 的日志输出
         */
        private List<String> ignoreHeaderNameList;

        /**
         * 黑名单url
         */
        private List<String> logUrlBlacklist;

        /**
         * 白名单url
         */
        private List<String> logUrlWhitelist;

        public LogProperties() {
            this.logUrlBlacklist = getDefaultLogUrlBlacklist();
        }

        public void setLogUrlBlacklist(List<String> logUrlBlacklist) {
            this.logUrlBlacklist = logUrlBlacklist;
            if (this.logUrlBlacklist == null) {
                this.logUrlBlacklist = getDefaultLogUrlBlacklist();
            } else {
                this.logUrlBlacklist.addAll(getDefaultLogUrlBlacklist());
            }
        }

        /**
         * 获取默认黑名单url列表
         *
         * @return 返回默认黑名单url列表
         */
        private List<String> getDefaultLogUrlBlacklist() {
            List<String> defaultLogUrlBackList = Lists.newArrayList();
            defaultLogUrlBackList.add("v2/**");
            defaultLogUrlBackList.add("/actuator/**");
            defaultLogUrlBackList.add("/actuator");
            defaultLogUrlBackList.add("/error");
            defaultLogUrlBackList.add("/druid/**");
            defaultLogUrlBackList.add("/druid");
            return defaultLogUrlBackList;
        }
    }

    /**
     * 判断是否不输出日志
     *
     * @param uri uri 地址
     * @return 返回结果 true: 是 false: 否
     */
    public boolean isNoPrintLog(String uri) {
        if (!log.isEnable()) {
            return true;
        }
        if (!CollectionUtils.isEmpty(log.getLogUrlWhitelist())
                && PatternMatchUtil.simpleMatch(log.getLogUrlWhitelist(), uri)) {
            // 白名单不为空，并且存在于白名单中
            return false;
        }
        // 黑名单不为空，并且存在于黑名单中
        return !CollectionUtils.isEmpty(log.getLogUrlBlacklist())
                && PatternMatchUtil.simpleMatch(log.getLogUrlBlacklist(), uri);
    }
}
