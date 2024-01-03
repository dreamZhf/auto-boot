package com.auto.boot.starter.common.filter;

import com.auto.boot.common.utils.MDCUtil;
import com.auto.boot.starter.common.properties.AutoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.servlet.*;
import java.io.IOException;

/**
 * trace filter
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultTraceFilter implements Filter, Ordered, AutoBootTraceFilter {

    private final AutoProperties autoProperties;

    public DefaultTraceFilter(AutoProperties autoProperties) {
        this.autoProperties = autoProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (!autoProperties.isTraceIdEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        try {
            MDCUtil.putTraceId();
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDCUtil.clear();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
