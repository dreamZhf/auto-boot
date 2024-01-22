package com.auto.boot.starter.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 过滤器枚举
 *
 * @author zhaohaifan
 */

@Getter
@AllArgsConstructor
public enum FilterEnum {

    /**
     * trace过滤器
     */
    TRACE_FILTER("TRACE_FILTER", "trace过滤器", -1),

    /**
     * 日志过滤器
     */
    LOG_FILTER("LOG_FILTER", "日志过滤器", 1),

    /**
     * 请求信息过滤器
     */
    REQUEST_INFO_FILTER("REQUEST_INFO_FILTER", "请求信息过滤器", 2),

    /**
     * 安全过滤器
     */
    SECURITY_FILTER("SECURITY_FILTER", "安全过滤器", 3),

    /**
     * token过滤器
     */
    TOKEN_FILTER("TOKEN_FILTER", "token过滤器", 4),

    /**
     * 默认过滤器
     */
    DEFAULT_FILTER("DEFAULT_FILTER", "默认过滤器", 99),
    ;
    final String code;
    final String msg;
    final int order;
}
