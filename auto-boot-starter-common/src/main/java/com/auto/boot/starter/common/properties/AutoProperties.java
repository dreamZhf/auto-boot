package com.auto.boot.starter.common.properties;

import com.auto.boot.starter.common.enums.FilterEnum;
import com.auto.boot.starter.common.utils.DefaultPropertiesUtil;
import com.auto.boot.starter.common.utils.PatternMatchUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

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
     * 过滤器顺序
     */
    private Map<String, Integer> filterOrderMap;

    /**
     * traceId 开关
     */
    private boolean traceIdEnable = true;

    public AutoProperties() {
        this.log = new LogProperties();
        this.filterOrderMap = DefaultPropertiesUtil.getDefaultFilterOrderMap();
    }

    /**
     * 设置过滤器顺序
     *
     * @param filterOrderMap 过滤器map
     */
    public void setFilterOrderMap(Map<String, Integer> filterOrderMap) {
        this.filterOrderMap = filterOrderMap;
        if (filterOrderMap == null) {
            this.filterOrderMap = DefaultPropertiesUtil.getDefaultFilterOrderMap();
            return;
        }
        for (FilterEnum e : FilterEnum.values()) {
            this.filterOrderMap.computeIfAbsent(e.getCode(), k -> e.getOrder());
        }
    }

    /**
     * 获取过滤器的顺序值
     *
     * @param filter 过滤器
     * @return 返回结果
     */
    public int getFilterOrder(String filter) {
        return filterOrderMap.getOrDefault(filter, FilterEnum.DEFAULT_FILTER.getOrder());
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
            this.logUrlBlacklist = DefaultPropertiesUtil.getDefaultLogUrlBlacklist();
        }

        public void setLogUrlBlacklist(List<String> logUrlBlacklist) {
            this.logUrlBlacklist = logUrlBlacklist;
            if (this.logUrlBlacklist == null) {
                this.logUrlBlacklist = DefaultPropertiesUtil.getDefaultLogUrlBlacklist();
                return;
            }
            this.logUrlBlacklist.addAll(DefaultPropertiesUtil.getDefaultLogUrlBlacklist());
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
