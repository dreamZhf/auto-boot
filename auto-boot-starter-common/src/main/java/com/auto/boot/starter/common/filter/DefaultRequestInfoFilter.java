package com.auto.boot.starter.common.filter;

import com.auto.boot.starter.common.enums.FilterEnum;
import com.auto.boot.starter.common.properties.AutoProperties;
import com.auto.boot.starter.common.subject.RequestInfoDTO;
import com.auto.boot.starter.common.subject.Subject;
import com.auto.boot.starter.common.utils.IPUtil;
import com.auto.boot.starter.common.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求信息 filter
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultRequestInfoFilter extends AbstractFilter implements IRequestInfoFilter {

    public DefaultRequestInfoFilter(AutoProperties autoProperties) {
        super(autoProperties);
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public void adapterDoFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("进入 请求信息 filter");
            }
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            RequestInfoDTO dto = new RequestInfoDTO();
            dto.setIp(IPUtil.getRealIp(httpServletRequest));
            dto.setHeaderMap(RequestUtil.getAllHeaderMap(httpServletRequest));
            Subject.add(dto);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            Subject.remove();
            if (log.isDebugEnabled()) {
                log.debug("清除请求线程上下文信息");
            }
        }
    }

    @Override
    public int getOrder() {
        return autoProperties.getFilterOrder(FilterEnum.REQUEST_INFO_FILTER.getCode());
    }
}
