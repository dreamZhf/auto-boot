package com.auto.boot.starter.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auto.boot.common.constant.GlobalConstant;
import com.auto.boot.common.model.dto.TokenInfoDTO;
import com.auto.boot.common.utils.JwtUtl;
import com.auto.boot.starter.common.utils.PatternMatchUtil;
import com.auto.boot.starter.security.model.dto.CheckTokenResultDTO;
import com.auto.boot.starter.security.properties.AutoSecurityProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽象token服务
 *
 * @author zhaohaifan
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractTokenService implements ITokenService {

    /**
     * 安全配置
     */
    protected final AutoSecurityProperties autoSecurityProperties;

    @Override
    public CheckTokenResultDTO checkToken(HttpServletRequest request) {
        String token = request.getHeader(GlobalConstant.HEADER_X_TOKEN);
        CheckTokenResultDTO dto = new CheckTokenResultDTO();
        dto.setResult(true);
        if (StringUtils.isBlank(token)) {
            log.info("token is null");
            TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
            dto.setTokenInfoDTO(tokenInfoDTO);
            return dto;
        }
        DecodedJWT decodedJWT = JwtUtl.decodeToken(token, autoSecurityProperties.getTokenSecret());
        doCheckToken(token, decodedJWT);
        dto.setTokenInfoDTO(analysisTokenInfo(decodedJWT));
        return dto;
    }

    /**
     * 实际校验 token
     *
     * @param token token 信息
     * @param jwt jwt信息
     */
    protected void doCheckToken(CheckTokenResultDTO dto, String token, DecodedJWT jwt) {

    }

    /**
     * 通过 jwt 信息解析出 token info
     *
     * @param decodedJWT jwt信息
     * @return 返回 token info
     */
    protected abstract TokenInfoDTO analysisTokenInfo(DecodedJWT decodedJWT);
}
