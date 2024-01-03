package com.auto.boot.common.utils;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * MDC工具类
 *
 * @author zhaohaifan
 */
public class MDCUtil {

    private MDCUtil() {}

    /**
     * 系统内部日志跟踪id名
     */
    public static final String LOG_TRACE_ID = "traceId";

    /**
     * 获取 系统内部traceId
     *
     * @return 返回 traceId
     */
    public static String getTraceId() {
        return MDC.get(LOG_TRACE_ID);
    }

    /**
     * putTraceId
     */
    public static void putTraceId() {
        putTraceId(getUuid());
    }

    /**
     * putTraceId
     * @param traceId 跟踪 ID
     */
    public static void putTraceId(String traceId) {
        MDC.put(LOG_TRACE_ID, traceId);
    }

    /**
     * 清理
     */
    public static void clear() {
        MDC.clear();
    }

    /**
     * 获取 uuid
     * @return 返回 UUID
     */
    private static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
