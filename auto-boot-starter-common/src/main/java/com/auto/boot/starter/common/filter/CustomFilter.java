package com.auto.boot.starter.common.filter;

import com.auto.boot.common.model.vo.ResultVO;
import com.auto.boot.common.utils.JsonUtil;
import org.slf4j.Logger;

import javax.servlet.Filter;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 自定义 filter
 *
 * @author zhaohaifan
 */
public interface CustomFilter extends Filter {

    /**
     * 获取日志
     * @return 返回日志
     */
    Logger getLog();

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
