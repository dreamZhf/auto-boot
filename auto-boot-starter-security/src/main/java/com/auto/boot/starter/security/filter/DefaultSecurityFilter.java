package com.auto.boot.starter.security.filter;

import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.model.vo.ResultVO;
import com.auto.boot.starter.common.enums.FilterEnum;
import com.auto.boot.starter.common.filter.AbstractFilter;
import com.auto.boot.starter.common.properties.AutoProperties;
import com.auto.boot.starter.security.service.ISecurityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 安全 filter
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultSecurityFilter extends AbstractFilter implements ISecurityFilter {

    private final ISecurityService iSecurityService;

    public DefaultSecurityFilter(AutoProperties autoProperties, ISecurityService iSecurityService) {
        super(autoProperties);
        this.iSecurityService = iSecurityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (iSecurityService.checkLogin(request)) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        responseMsg(servletResponse, ResultVO.fail(ResultEnum.TOKEN_INVALID));
    }

    @Override
    public int getOrder() {
        return autoProperties.getFilterOrder(FilterEnum.SECURITY_FILTER.getCode());
    }

    @Override
    public Logger getLog() {
        return log;
    }
}
