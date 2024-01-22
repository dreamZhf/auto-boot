package com.auto.boot.starter.security.service;

import com.auto.boot.starter.common.utils.PatternMatchUtil;
import com.auto.boot.starter.security.properties.AutoSecurityProperties;
import lombok.AllArgsConstructor;

/**
 * 抽象安全服务
 *
 * @author zhaohaifan
 */
@AllArgsConstructor
public abstract class AbstractSecurityService implements ISecurityService {

    /**
     * 安全配置
     */
    protected final AutoSecurityProperties autoSecurityProperties;

    /**
     * 判断是否不需要校验
     *
     * @param uri uri地址
     * @return 返回结果
     */
    protected boolean isNotVerify(String uri) {
        return PatternMatchUtil.simpleMatch(autoSecurityProperties.getTokenVerifyWhiteUrls(), uri);
    }
}
