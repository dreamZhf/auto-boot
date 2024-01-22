package com.auto.boot.starter.security.service;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全 service
 */
public interface ISecurityService {

    /**
     * 获取日志
     *
     * @return 返回日志
     */
    Logger getLog();

    /**
     * 校验是否登录
     *
     * @param request 请求
     * @return 返回结果 true: 校验成功 false: 校验失败
     */
    boolean checkLogin(HttpServletRequest request);
}
