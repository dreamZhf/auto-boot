package com.auto.boot.starter.common.subject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求信息
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString
public class RequestInfoDTO {

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

    /**
     * 签名密钥
     */
    private String signSecret;

    /**
     * 加解密密钥
     */
    private String secret;

    /**
     * 请求的IP地址
     */
    private String ip;
}
