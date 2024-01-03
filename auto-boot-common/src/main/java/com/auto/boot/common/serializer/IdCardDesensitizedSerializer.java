package com.auto.boot.common.serializer;

import com.auto.boot.common.utils.DesensitizationUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 身份证号 脱敏
 *
 * @author dream
 */
public class IdCardDesensitizedSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (StringUtils.isBlank(s)) {
            jsonGenerator.writeString(s);
            return;
        }
        jsonGenerator.writeString(DesensitizationUtil.idCard(s));
    }
}
