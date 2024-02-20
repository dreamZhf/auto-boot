package com.auto.boot.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auto.boot.common.constant.GlobalConstant;
import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.exception.BizException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * jwt 工具类
 *
 * @author zhaohaifan
 */
@Slf4j
public class JwtUtl {

    private static final Map<String, Algorithm> ALGORITHM_MAP = Maps.newConcurrentMap();

    private JwtUtl() {}

    /**
     * 获取 Algorithm
     *
     * @param secret 密钥
     * @return 返回 Algorithm
     */
    private static Algorithm getAlgorithm(String secret) {
        Algorithm algorithm = ALGORITHM_MAP.get(secret);
        if (algorithm != null) {
            return algorithm;
        }
        synchronized (JwtUtl.class) {
            algorithm = ALGORITHM_MAP.get(secret);
            if (algorithm != null) {
                return algorithm;
            }
            algorithm = Algorithm.HMAC256(secret);
            ALGORITHM_MAP.put(secret, algorithm);
            return algorithm;
        }
    }

    /**
     * 创建 token
     *
     * @param tokenId token id
     * @param secret token 密钥
     * @param data 承载数据
     * @return 返回 token信息
     */
    public static String createToken(String tokenId, String secret, String data) {
        log.info(data);
        Algorithm algorithm = getAlgorithm(secret);
        return JWT.create()
                .withJWTId(tokenId)
                .withClaim(GlobalConstant.JWT_CIPHERTEXT_BODY_KEY, data)
                .sign(algorithm);
    }

    /**
     * 创建 token
     *
     * @param tokenId token id
     * @param secret token 密钥
     * @param data 承载数据
     * @return 返回 token信息
     */
    public static String createToken(String tokenId, String secret, Map<String, String> data) {
        Algorithm algorithm = getAlgorithm(secret);
        JWTCreator.Builder builder = JWT.create()
                .withJWTId(tokenId);
        data.forEach(builder::withClaim);
        return builder.sign(algorithm);
    }

    /**
     * 解析并校验 token
     *
     * @param token token 信息
     * @param secret token 密钥
     * @return 返回 body 内容
     */
    public static DecodedJWT decodeToken(String token, String secret) {
        try {
            JWTVerifier jwtVerifier = JWT.require(getAlgorithm(secret)).build();
            return jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            log.error("token 校验失败", e);
            throw new BizException(ResultEnum.TOKEN_INVALID);
        }
    }
}
