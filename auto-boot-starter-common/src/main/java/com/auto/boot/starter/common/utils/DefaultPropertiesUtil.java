package com.auto.boot.starter.common.utils;

import com.auto.boot.starter.common.enums.FilterEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 默认配置工具类
 *
 * @author zhaohaifan
 */
public class DefaultPropertiesUtil {

    private DefaultPropertiesUtil() {}

    /**
     * 获取默认黑名单url列表
     *
     * @return 返回默认黑名单url列表
     */
    public static List<String> getDefaultLogUrlBlacklist() {
        List<String> defaultLogUrlBackList = Lists.newArrayList();
        defaultLogUrlBackList.add("v2/**");
        defaultLogUrlBackList.add("/actuator/**");
        defaultLogUrlBackList.add("/actuator");
        defaultLogUrlBackList.add("/error");
        defaultLogUrlBackList.add("/druid/**");
        defaultLogUrlBackList.add("/druid");
        return defaultLogUrlBackList;
    }

    /**
     * 获取默认的过滤器顺序配置
     *
     * @return 返回默认的过滤器顺序配置
     */
    public static Map<String, Integer> getDefaultFilterOrderMap() {
        Map<String, Integer> map = Maps.newHashMap();
        for (FilterEnum e : FilterEnum.values()) {
            map.put(e.getCode(), e.getOrder());
        }
        return map;
    }
}
