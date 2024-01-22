package com.auto.boot.starter.common;

import com.auto.boot.starter.common.advice.DefaultWebControllerAdvice;
import com.auto.boot.starter.common.advice.IWebControllerAdvice;
import com.auto.boot.starter.common.exception.DefaultGlobalExceptionHandler;
import com.auto.boot.starter.common.exception.IGlobalExceptionHandler;
import com.auto.boot.starter.common.filter.ILogFilter;
import com.auto.boot.starter.common.filter.ITraceFilter;
import com.auto.boot.starter.common.filter.DefaultLogFilter;
import com.auto.boot.starter.common.filter.DefaultTraceFilter;
import com.auto.boot.starter.common.properties.AutoProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置
 *
 * @author zhaohaifan
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties({AutoProperties.class})
public class CommonAutoConfiguration {

    /**
     * 创建 LogFilter
     *
     * @param autoProperties 配置信息
     * @return 返回 LogFilter
     */
    @Bean
    @ConditionalOnMissingBean(ILogFilter.class)
    public DefaultLogFilter logFilter(AutoProperties autoProperties) {
        log.info("create logFilter");
        return new DefaultLogFilter(autoProperties);
    }

    /**
     * 创建 traceFilter
     *
     * @param autoProperties 配置信息
     * @return 返回 LogFilter
     */
    @Bean
    @ConditionalOnMissingBean(ITraceFilter.class)
    public DefaultTraceFilter traceFilter(AutoProperties autoProperties) {
        log.info("create traceFilter");
        return new DefaultTraceFilter(autoProperties);
    }

    /**
     * 创建 globalExceptionHandler
     *
     * @return 返回 globalExceptionHandler
     */
    @Bean
    @ConditionalOnMissingBean(IGlobalExceptionHandler.class)
    public DefaultGlobalExceptionHandler globalExceptionHandler() {
        log.info("create GlobalExceptionHandler");
        return new DefaultGlobalExceptionHandler();
    }

    /**
     * 创建 webControllerAdvice
     *
     * @return 返回 webControllerAdvice
     */
    @Bean
    @ConditionalOnMissingBean(IWebControllerAdvice.class)
    public DefaultWebControllerAdvice webControllerAdvice(AutoProperties autoProperties) {
        log.info("create WebControllerAdvice");
        return new DefaultWebControllerAdvice(autoProperties);
    }
}
