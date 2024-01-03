package com.auto.boot.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * base 64 工具类
 *
 * @author zhaohaifan
 */
public class Base64Util extends Base64 {

    /**
     * 对字符串进行 base64编码
     * @param str 字符串内容
     * @return 返回 base64值
     */
    public static String encodeBase64String(final String str) {
        return encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对 base64 解码为 字符串
     * @param base64String base64内容
     * @return 返回解码后的内容
     */
    public static String decodeBase64String(final String base64String) {
        byte[] bytes = decodeBase64(base64String);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
