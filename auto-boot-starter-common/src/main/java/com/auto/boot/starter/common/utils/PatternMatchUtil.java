package com.auto.boot.starter.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.PatternMatchUtils;

import java.util.List;

/**
 * 正则匹配工具类
 *
 * @author zhaohaifan
 */
public class PatternMatchUtil {

    /**
     * 根据指定的正则匹配
     *
     * @param patterns 规则列表
     * @param str 字符串
     * @return 返回结果
     */
    public static boolean simpleMatch(@Nullable List<String> patterns, String str) {
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        for (String pattern : patterns) {
            if (PatternMatchUtils.simpleMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }
}
