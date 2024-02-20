package com.auto.boot.starter.security.model.dto;

import com.auto.boot.common.model.dto.TokenInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * token 校验结果
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString
public class CheckTokenResultDTO {

    /**
     * 校验结果
     */
    private boolean result;

    /**
     * token 信息
     */
    private TokenInfoDTO tokenInfoDTO;
}
