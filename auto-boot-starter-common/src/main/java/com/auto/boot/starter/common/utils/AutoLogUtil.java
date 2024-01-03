package com.auto.boot.starter.common.utils;

import com.auto.boot.starter.common.jackson.CoreJavaTimeModule;
import com.auto.boot.common.utils.DateTimeUtil;
import com.auto.boot.starter.common.jackson.LogModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日志工具类
 *
 * @author zhaohaifan
 */
@Slf4j
public class AutoLogUtil {

    /**
     * 日志参数长度
     */
    public static final int LOG_LENGTH = 1000;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //设置地点为中国
        OBJECT_MAPPER.setLocale(Locale.CHINA);
        //去掉默认的时间戳格式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        //序列化时，日期的统一格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeUtil.FORMATTER_2, Locale.CHINA));
        // 单引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        OBJECT_MAPPER.findAndRegisterModules();
        //失败处理
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //反序列化时，属性不存在的兼容处理s
        OBJECT_MAPPER.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //日期格式化
        OBJECT_MAPPER.registerModule(new CoreJavaTimeModule());
        OBJECT_MAPPER.registerModule(new LogModule());
        OBJECT_MAPPER.findAndRegisterModules();
    }

   private AutoLogUtil() {
   }

    /**
     * 将日志信息限定到内定长度
     *
     * @param str 日志信息
     * @return 返回截取后的日志
     */
   public static String handleLogStr(String str) {
       if (StringUtils.isBlank(str)) {
           return str;
       }
       if (str.length() > LOG_LENGTH) {
           str = str.substring(0, LOG_LENGTH) + "......";
       }
       return str;
   }

    /**
     * 序列化为 json
     *
     * @param <T> 泛型
     * @return 返回序列化的 json 字符串
     */
    public static <T> String toLogJson(T o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return handleLogStr((String) o);
        }
        String str = null;
        try {
             str = OBJECT_MAPPER.writeValueAsString(o);
        } catch (Exception e) {
            log.error("日志JSON格式化异常", e);
        }
        return str;
    }
}
