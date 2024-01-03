package com.auto.boot.starter.common.filter;

import com.auto.boot.common.utils.JsonUtil;
import com.auto.boot.common.utils.ObjectUtil;
import com.auto.boot.starter.common.properties.AutoProperties;
import com.auto.boot.starter.common.utils.IPUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 默认日志 filter
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultLogFilter implements Filter, Ordered, AutoBootLogFilter {

    private final AutoProperties autoProperties;

    public DefaultLogFilter(AutoProperties autoProperties) {
        this.autoProperties = autoProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (autoProperties.isNoPrintLog(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (autoProperties.getLog().isHeader()) {
            log.info("请求URL: {}, 请求类型为: {}, 请求IP地址为: {}, 请求header: {}",request.getRequestURL(),
                    request.getMethod(), IPUtil.getRealIp(request), JsonUtil.toJson(getHeaderMap(request)));
        } else {
            log.info("请求URL: {}, 请求类型为: {}, 请求IP地址为: {}", request.getRequestURL(),
                    request.getMethod(), IPUtil.getRealIp(request));
        }
        printFormDataLog(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }


    /**
     * 输出 formData 日志
     *
     * @param request http 请求
     */
    private void printFormDataLog(HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, request.getParameter(key));
        }
        if (!ObjectUtil.isEmpty(map)) {
            log.info("请求URI: {}, 请求参数 form data: {}", request.getRequestURI(), JsonUtil.toJson(map));
        }
    }

    /**
     * 获取 header map
     *
     * @param request 请求
     * @return 返回 header map
     */
    private Map<String, Object> getHeaderMap(HttpServletRequest request) {
        Enumeration<String> headers = request == null ? null : request.getHeaderNames();
        Map<String, Object> headerMap = Maps.newHashMap();
        if (headers == null) {
            return headerMap;
        }
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            if (isIgnoreHeader(headerName)) {
                continue;
            }
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        return headerMap;
    }

    /**
     * 是否忽略header
     *
     * @param headerName header名称
     * @return 返回结果
     */
    private boolean isIgnoreHeader(String headerName) {
        List<String> list = autoProperties.getLog().getIgnoreHeaderNameList();
        if (list == null || list.isEmpty()) {
            return false;
        }
        return list.contains(headerName);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
