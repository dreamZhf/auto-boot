package com.auto.boot.starter.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 日志 对象 序列化
 *
 * @author dream
 */
public class LogObjectSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeString((String) null);
        }
        if (value instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) value;
            String str = file.getOriginalFilename();
            str = StringUtils.isBlank(str) ? file.getName() : str;
            gen.writeString(str);
            return;
        }
        if (value instanceof File) {
            File file = (File) value;
            gen.writeString(file.getName());
            return;
        }
        gen.writeString((String) null);
    }
}
