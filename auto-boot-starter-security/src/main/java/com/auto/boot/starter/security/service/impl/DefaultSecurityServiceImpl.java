package com.auto.boot.starter.security.service.impl;

import com.auto.boot.common.constant.GlobalConstant;
import com.auto.boot.starter.security.properties.AutoSecurityProperties;
import com.auto.boot.starter.security.service.AbstractSecurityService;
import com.auto.boot.starter.security.service.ISecurityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全校验默认服务
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultSecurityServiceImpl extends AbstractSecurityService implements ISecurityService {

    public DefaultSecurityServiceImpl(AutoSecurityProperties autoSecurityProperties) {
        super(autoSecurityProperties);
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public boolean checkLogin(HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.info("【安全校验service】地址: {}",uri);
        // 过滤掉不需要校验的 uri
        if (!autoSecurityProperties.isEnabled() || isNotVerify(uri)) {
            log.info("【安全校验service】放行: {}",uri);
            return true;
        }
        String token = request.getHeader(GlobalConstant.HEADER_X_TOKEN);
        if (StringUtils.isBlank(token)) {
            log.info("【安全校验service】token 为空");
            return false;
        }
        return true;
    }
}
