package com.auto.boot.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 字符串序列化, 将 Integer 转换为 String
 *
 * @author zhaohaifan
 */
public class IntegerConvertStringSerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeString((String) null);
            return;
        }
        gen.writeString(String.valueOf(value));
    }
}
