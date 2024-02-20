package com.auto.boot.starter.common.filter;

import com.auto.boot.common.model.vo.ResultVO;
import com.auto.boot.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.springframework.core.Ordered;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * filter
 *
 * @author zhaohaifan
 */
public interface IFilter extends Filter, Ordered {

    /**
     * 获取日志
     * @return 返回日志
     */
    Logger getLog();

    /**
     * 适配 doFilter
     *
     * @param servletRequest 请求
     * @param servletResponse 响应
     * @param filterChain filterChain
     */
    default void adapterDoFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

    }

    /**
     * 返回信息
     * @param servletResponse response
     * @param response 返回信息
     */
    default void responseMsg(ServletResponse servletResponse, ResultVO<?> response) {
        PrintWriter writer = null;
        try {
            servletResponse.setCharacterEncoding("utf-8");
            servletResponse.setContentType("text/json;charset=utf-8");
            writer = servletResponse.getWriter();
            String result = JsonUtil.toJson(response);
            getLog().info("响应参数: {}", result);
            writer.write(result);
            writer.flush();
        } catch (Exception e) {
            getLog().error("返回信息异常", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
