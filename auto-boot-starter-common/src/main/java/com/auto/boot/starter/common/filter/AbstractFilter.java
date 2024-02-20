package com.auto.boot.starter.common.filter;

import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.exception.AutoException;
import com.auto.boot.common.model.vo.ResultVO;
import com.auto.boot.starter.common.properties.AutoProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author zhaohaifan
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractFilter implements IFilter {

    protected final AutoProperties autoProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            adapterDoFilter(servletRequest, servletResponse, filterChain);
        } catch (AutoException e) {
            responseMsg(servletResponse, ResultVO.fail(e.getResultCode(), e.getMessage()));
        } catch (Exception e) {
            log.error("过滤器执行异常", e);
            responseMsg(servletResponse, ResultVO.fail(ResultEnum.BIZ_FAIL));
        }
    }
}
