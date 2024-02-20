package com.auto.boot.starter.security.service.impl;

import com.auto.boot.common.constant.GlobalConstant;
import com.auto.boot.common.model.dto.TokenInfoDTO;
import com.auto.boot.starter.security.model.dto.CheckTokenResultDTO;
import com.auto.boot.starter.security.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认 token service impl
 * 默认使用 jwt 生成与校验 token
 *
 * @author zhaohaifan
 */
@Slf4j
public class DefaultTokenServiceImpl implements ITokenService {

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public CheckTokenResultDTO checkToken(HttpServletRequest request) {
        String token = request.getHeader(GlobalConstant.HEADER_X_TOKEN);
        CheckTokenResultDTO dto = new CheckTokenResultDTO();
        dto.setResult(true);
        TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
        dto.setTokenInfoDTO(tokenInfoDTO);
        if (StringUtils.isBlank(token)) {
            log.info("token is null");
            return dto;
        }
        return dto;
    }
}
