package com.auto.boot.starter.security.properties;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 安全 配置
 *
 * @author zhaohaifan
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = AutoSecurityProperties.PREFIX)
public class AutoSecurityProperties {

    protected static final String PREFIX = "auto-boot.security";

    /**
     * 开关，默认打开
     */
    private boolean enabled = true;

    /**
     * token 校验 url白名单
     */
    private List<String> tokenVerifyWhiteUrls;

    /**
     * 续期 url 白名单
     */
    private List<String> renewTokenWhiteUrls;

    public AutoSecurityProperties() {
        this.tokenVerifyWhiteUrls = getDefaultTokenVerifyWhiteUrls();
    }

    public void setTokenVerifyWhiteUrls(List<String> tokenVerifyWhiteUrls) {
        this.tokenVerifyWhiteUrls = tokenVerifyWhiteUrls;
        if (this.tokenVerifyWhiteUrls == null) {
            this.tokenVerifyWhiteUrls = getDefaultTokenVerifyWhiteUrls();
        } else {
            this.tokenVerifyWhiteUrls.addAll(getDefaultTokenVerifyWhiteUrls());
        }
    }

    /**
     * 获取默认的 token 校验白名单
     *
     * @return 返回列表
     */
    private List<String> getDefaultTokenVerifyWhiteUrls() {
        List<String> defaultTokenVerifyWhiteUrls = Lists.newArrayList();
        defaultTokenVerifyWhiteUrls.add("v2/**");
        defaultTokenVerifyWhiteUrls.add("/actuator/**");
        defaultTokenVerifyWhiteUrls.add("/actuator");
        defaultTokenVerifyWhiteUrls.add("/error");
        defaultTokenVerifyWhiteUrls.add("/druid/**");
        defaultTokenVerifyWhiteUrls.add("/druid");
        return defaultTokenVerifyWhiteUrls;
    }
}
