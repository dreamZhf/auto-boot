package com.auto.boot.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * String 反序列化为 Long
 *
 * @author zhaohaifan
 */
@Slf4j
public class StringConvertLongDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String str = p.getText();
        try {
            if (StringUtils.isBlank(str)) {
                return null;
            }
            return Long.parseLong(str);
        } catch (Exception e) {
            log.error("无法将 String 转换为 Long, str: {}", str);
        }
        return null;
    }
}
