package com.auto.boot.starter.common.subject;

import com.auto.boot.common.model.dto.TokenInfoDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

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
     * token 信息
     */
    private TokenInfoDTO tokenInfoDTO;

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

    /**
     * header 信息
     */
    private Map<String, String> headerMap;
}
