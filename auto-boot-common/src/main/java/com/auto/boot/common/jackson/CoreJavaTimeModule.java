package com.auto.boot.common.jackson;

import com.auto.boot.common.utils.DateTimeUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间默认序列化
 *
 * @author zhaohaifan
 */
public class CoreJavaTimeModule extends SimpleModule {

    private static final long serialVersionUID = -4747810409872266987L;

    public CoreJavaTimeModule() {
        super(PackageVersion.VERSION);
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtil.DATETIME_FORMATTER_2));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeUtil.DATETIME_FORMATTER_3));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeUtil.DATETIME_FORMATTER_4));
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtil.DATETIME_FORMATTER_2));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeUtil.DATETIME_FORMATTER_3));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeUtil.DATETIME_FORMATTER_4));
    }
}
