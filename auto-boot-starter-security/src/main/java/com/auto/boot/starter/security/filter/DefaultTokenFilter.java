package com.auto.boot.starter.security.filter;

import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.exception.BizException;
import com.auto.boot.common.model.vo.ResultVO;
import com.auto.boot.starter.common.enums.FilterEnum;
import com.auto.boot.starter.common.filter.AbstractFilter;
import com.auto.boot.starter.common.properties.AutoProperties;
import com.auto.boot.starter.common.subject.Subject;
import com.auto.boot.starter.security.model.dto.CheckTokenResultDTO;
import com.auto.boot.starter.security.properties.AutoSecurityProperties;
import com.auto.boot.starter.security.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 默认 token filter 实现
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultTokenFilter extends AbstractFilter implements ITokenFilter {

    private final ITokenService iTokenService;
    public DefaultTokenFilter(AutoProperties autoProperties, ITokenService iTokenService) {
        super(autoProperties);
        this.iTokenService = iTokenService;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public void adapterDoFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("进入 token filter");
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        CheckTokenResultDTO resultDTO = iTokenService.checkToken(request);
        if (resultDTO.isResult()) {
            Subject.addTokenInfoDTO(resultDTO.getTokenInfoDTO());
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        responseMsg(servletResponse, ResultVO.fail(ResultEnum.TOKEN_INVALID));
    }

    @Override
    public int getOrder() {
        return autoProperties.getFilterOrder(FilterEnum.TOKEN_FILTER.getCode());
    }
}
