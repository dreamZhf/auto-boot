package com.auto.boot.starter.security;

import com.auto.boot.starter.common.properties.AutoProperties;
import com.auto.boot.starter.security.filter.DefaultSecurityFilter;
import com.auto.boot.starter.security.filter.DefaultTokenFilter;
import com.auto.boot.starter.security.filter.ISecurityFilter;
import com.auto.boot.starter.security.filter.ITokenFilter;
import com.auto.boot.starter.security.properties.AutoSecurityProperties;
import com.auto.boot.starter.security.service.ISecurityService;
import com.auto.boot.starter.security.service.ITokenService;
import com.auto.boot.starter.security.service.impl.DefaultSecurityServiceImpl;
import com.auto.boot.starter.security.service.impl.DefaultTokenServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 安全自动配置
 *
 * @author zhaohaifan
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties({AutoSecurityProperties.class})
public class SecurityAutoConfiguration {

    /**
     * 创建 securityService
     *
     * @return 返回 ISecurityService
     */
    @Bean
    @ConditionalOnMissingBean(ISecurityService.class)
    public ISecurityService securityService(AutoSecurityProperties autoSecurityProperties) {
        log.info("create securityService");
        return new DefaultSecurityServiceImpl(autoSecurityProperties);
    }

    /**
     * 创建 securityFilter
     *
     * @return 返回 ISecurityFilter
     */
    @Bean
    @ConditionalOnMissingBean(ISecurityFilter.class)
    public ISecurityFilter securityFilter(AutoProperties autoProperties, ISecurityService securityService) {
        log.info("create securityFilter");
        return new DefaultSecurityFilter(autoProperties, securityService);
    }

    /**
     * 创建 tokenService
     *
     * @return 返回 ISecurityService
     */
    @Bean
    @ConditionalOnMissingBean(ITokenService.class)
    public ITokenService tokenService() {
        log.info("create tokenService");
        return new DefaultTokenServiceImpl();
    }

    /**
     * 创建 securityFilter
     *
     * @return 返回 ISecurityFilter
     */
    @Bean
    @ConditionalOnMissingBean(ITokenFilter.class)
    public ITokenFilter tokenFilter(AutoProperties autoProperties, ITokenService tokenService) {
        log.info("create tokenFilter");
        return new DefaultTokenFilter(autoProperties, tokenService);
    }
}
