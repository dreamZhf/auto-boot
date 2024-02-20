package com.auto.boot.starter.common.filter;

import com.auto.boot.common.utils.MDCUtil;
import com.auto.boot.starter.common.enums.FilterEnum;
import com.auto.boot.starter.common.properties.AutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * trace filter
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultTraceFilter extends AbstractFilter implements ITraceFilter {

    public DefaultTraceFilter(AutoProperties autoProperties) {
        super(autoProperties);
    }

    @Override
    public void adapterDoFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("进入 trace filter, traceIdEnable: {}", autoProperties.isTraceIdEnable());
        }
        if (!autoProperties.isTraceIdEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            MDCUtil.putTraceId();
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDCUtil.clear();
            if (log.isDebugEnabled()) {
                log.debug("清除请求线程中 traceId 信息");
            }
        }
    }

    @Override
    public int getOrder() {
        return autoProperties.getFilterOrder(FilterEnum.TRACE_FILTER.getCode());
    }

    @Override
    public Logger getLog() {
        return log;
    }
}
