package com.auto.boot.starter.mybatis.template;

/**
 * 业务执行器
 *
 * @author zhaohaifan
 */
public interface ServiceActuator<T> {

    /**
     * 执行业务前
     *
     * @throws Exception 异常
     */
    default void before() throws Exception {}

    /**
     * 执行业务
     *
     * @throws Exception 异常
     * @return 返回数据
     */
     T execute() throws Exception;
}
