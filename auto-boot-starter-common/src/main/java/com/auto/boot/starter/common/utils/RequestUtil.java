package com.auto.boot.starter.common.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * 请求工具
 *
 * @author zhaohaifan
 */
@Slf4j
public class RequestUtil {

    private RequestUtil() {}

    /**
     * 获取 HttpServletRequest
     *
     * @return 返回HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        if(null == RequestContextHolder.getRequestAttributes()){
            return null;
        }else{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
        }
    }

    /**
     * 获取 HttpServletRequest 不做校验是否为空
     *
     * @return 返回HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequestNotValid() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return 返回 HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getResponse();
    }

    /**
     * 获取 HttpServletResponse 不做校验是否为空
     *
     * @return 返回 HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponseNotValid() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getResponse();
    }

    /**
     * 获取所有的 header 信息
     *
     * @param request 请求
     * @return 返回 header map
     */
    public static Map<String, String> getAllHeaderMap(HttpServletRequest request) {
        Enumeration<String> headers = request == null ? null : request.getHeaderNames();
        Map<String, String> headerMap = Maps.newHashMap();
        if (headers == null) {
            return headerMap;
        }
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        return headerMap;
    }
}
