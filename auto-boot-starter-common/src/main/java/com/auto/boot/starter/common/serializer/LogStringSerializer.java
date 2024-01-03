package com.auto.boot.starter.common.serializer;

import com.auto.boot.starter.common.utils.AutoLogUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 日志 字符串 序列化
 *
 * @author dream
 */
public class LogStringSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (StringUtils.isBlank(s)) {
            jsonGenerator.writeString(s);
            return;
        }
        jsonGenerator.writeString(AutoLogUtil.handleLogStr(s));
    }
}
