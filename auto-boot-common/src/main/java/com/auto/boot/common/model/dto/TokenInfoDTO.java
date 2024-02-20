package com.auto.boot.common.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * token 信息
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString
public class TokenInfoDTO {

    /**
     * token id
     */
    private String id;

    /**
     * 用户ID
     */
    private Long userBizId;

    /**
     * token 信息
     */
    private String token;
}
