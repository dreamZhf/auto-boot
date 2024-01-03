package com.auto.boot.starter.common.advice;

import com.auto.boot.common.utils.ObjectUtil;
import com.auto.boot.starter.common.properties.AutoProperties;
import com.auto.boot.starter.common.subject.Subject;
import com.auto.boot.starter.common.utils.AutoLogUtil;
import com.auto.boot.starter.common.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 参数统一处理
 * @author dream
 */
@Slf4j
@RestControllerAdvice
public class DefaultWebControllerAdvice extends RequestBodyAdviceAdapter implements ResponseBodyAdvice<Object>,
        IWebControllerAdvice {

    private final AutoProperties autoProperties;

    public DefaultWebControllerAdvice(AutoProperties autoProperties) {
        this.autoProperties = autoProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (autoProperties.isNoPrintLog(serverHttpRequest.getURI().getPath())) {
            return o;
        }
        String res = null;
        try {
            res = ObjectUtil.isBasicType(o) ? String.valueOf(o) : AutoLogUtil.toLogJson(o);
        } catch (Exception e) {
            log.error("result convert error", e);
        } finally {
            log.info("请求URL: {}, 响应参数: {}, userId: {}", serverHttpRequest.getURI(), res, getUserId());
        }
        return o;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        if (!autoProperties.getLog().isEnable()) {
            return body;
        }
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        String uri = request.getRequestURI();
        if (autoProperties.isNoPrintLog(uri)) {
            return body;
        }
        try {
            Method method = parameter.getMethod();
            String methodInfo = null;
            if (method != null) {
                methodInfo = String.format("%s.%s",method.getDeclaringClass().getName(), method.getName());
            }

            log.info("请求URI: {}, 请求参数 body: {}, userId: {}, {}, ", uri,
                    AutoLogUtil.toLogJson(body), getUserId(), methodInfo);
        } catch (Exception e) {
            log.error("result convert error, body: {}", body, e);
        }
        return body;
    }

    /**
     * 获取 userId
     *
     * @return 返回 userId
     */
    private String getUserId() {
        return Subject.getCurrentUserIdStr();
    }
}
