package com.auto.boot.starter.mybatis;

/**
 * 执行标记
 *
 * @author zhaohaifan
 */
public class ContextInitializerFlag {

    /**
     * mybatis 初始化标记
     */
    public static volatile boolean myBatisContextInitializer = false;
}
