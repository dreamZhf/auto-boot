package com.auto.boot.common.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象工具类
 *
 * @author zhaohaifan
 */
public class ObjectUtil extends ObjectUtils {

    /**
     * 判断是否是基本类型(包含 BigDecimal, String)
     * @param obj 对象
     * @return true: 是 false: 否
     */
    public static boolean isBasicType(Object obj) {
        return obj instanceof String || obj instanceof Integer || obj instanceof   Long || obj instanceof Short
                || obj instanceof Byte || obj instanceof Double || obj instanceof Float || obj instanceof Boolean
                || obj instanceof BigDecimal;
    }

    /**
     * 判断是否是基本类型(不包含 BigDecimal, String)
     *
     * @param clazz 类型
     * @return true: 是 false: 否
     */
    public static boolean isBasicType(Class<?> clazz) {
        boolean flag = clazz.isPrimitive();
        if (flag) {
            return true;
        }
        try {
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将key value 数组转为 map
     *
     * @param keysValues key value 数组
     * @param <K>        key
     * @param <V>        value
     * @return map 集合
     */
    public static <K, V> Map<K, V> toMap(Object... keysValues) {
        int kvLength = keysValues.length;
        if (kvLength % 2 != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        }
        Map<K, V> keyValueMap = new HashMap<>(kvLength);
        for (int i = kvLength - 2; i >= 0; i -= 2) {
            Object key = keysValues[i];
            Object value = keysValues[i + 1];
            keyValueMap.put((K) key, (V) value);
        }
        return keyValueMap;
    }
}
