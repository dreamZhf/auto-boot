package com.auto.boot.starter.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * spring bean 工具类
 *
 * @author zhaohaifan
 */
public class SpringBeanUtil {

    private static ApplicationContext applicationContext;

    /**
     * 设置 applicationContext
     *
     * @param applicationContext 上下文
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * 获取 applicationContext
     *
     * @return 返回ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取 bean
     *
     * @param tClass bean类型
     * @return 返回 bean
     */
    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    /**
     * 获取 bean
     *
     * @param name bean名称
     * @return 返回 bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
