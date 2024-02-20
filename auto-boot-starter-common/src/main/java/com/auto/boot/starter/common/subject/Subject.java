package com.auto.boot.starter.common.subject;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.auto.boot.common.model.dto.TokenInfoDTO;

/**
 * subject 类
 *
 * @author zhaohaifan
 */
public class Subject {

    /**
     * 线程存储的信息
     */
    private static final TransmittableThreadLocal<RequestInfoDTO> THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 添加 RequestInfoDTO
     * @param requestInfoDTO 请求信息
     */
    public static void add(RequestInfoDTO requestInfoDTO) {
        THREAD_LOCAL.set(requestInfoDTO);
    }

    /**
     * 新增 TokenInfoDTO
     *
     * @param tokenInfoDTO token 信息
     */
    public static void addTokenInfoDTO(TokenInfoDTO tokenInfoDTO) {
        getRequestInfoDTO().setTokenInfoDTO(tokenInfoDTO);
    }

    /**
     * 清除掉信息
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static TokenInfoDTO getTokenInfoDTO() {
        RequestInfoDTO requestInfoDTO = getRequestInfoDTO();
        return requestInfoDTO == null ? null : requestInfoDTO.getTokenInfoDTO();
    }

    /**
     * 获取当前登录用户id
     *
     * @return 返回当前登录用户id
     */
    public static Long getCurrentUserId() {
        TokenInfoDTO tokenInfoDTO = getTokenInfoDTO();
        return tokenInfoDTO == null ? null : tokenInfoDTO.getUserBizId();
    }

    /**
     * 获取当前登录用户ID 字符串
     *
     * @return 返回当前登录用户ID 字符串
     */
    public static String getCurrentUserIdStr() {
        Long userBizId = getCurrentUserId();
        return userBizId == null ? null : String.valueOf(userBizId);
    }

    /**
     * 获取 加解密 密钥
     *
     * @return 返回结果
     */
    public static String getSecret() {
        return getRequestInfoDTO().getSecret();
    }

    /**
     * 获取签名密钥
     *
     * @return 返回签名密钥
     */
    public static String getSignSecret() {
        return getRequestInfoDTO().getSignSecret();
    }

    /**
     * 获取 ip 地址
     *
     * @return 返回 ip 地址
     */
    public static String getIp() {
        RequestInfoDTO infoDTO = getRequestInfoDTO();
        return infoDTO == null ? null : infoDTO.getIp();
    }

    /**
     * 获取 request Info
     *
     * @return 返回结果
     */
    public static RequestInfoDTO getRequestInfoDTO() {
        return THREAD_LOCAL.get();
    }
}
