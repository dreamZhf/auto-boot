package com.auto.boot.starter.security.service;

import com.auto.boot.starter.security.model.dto.CheckTokenResultDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * token service
 *
 * @author zhaohaifan
 */
public interface ITokenService {

    /**
     * 校验 token
     *
     * @param request 请求
     * @return 返回校验结果
     */
    CheckTokenResultDTO checkToken(HttpServletRequest request);
}
