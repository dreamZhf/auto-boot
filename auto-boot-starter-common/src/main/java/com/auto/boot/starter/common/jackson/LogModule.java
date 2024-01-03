package com.auto.boot.starter.common.jackson;

import com.auto.boot.starter.common.serializer.LogObjectSerializer;
import com.auto.boot.starter.common.serializer.LogStringSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import org.springframework.core.io.InputStreamSource;

import java.io.File;

/**
 * 日志默认序列化
 *
 * @author zhaohaifan
 */
public class LogModule extends SimpleModule {

    private static final long serialVersionUID = -8555286508685983493L;

    public LogModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(String.class, new LogStringSerializer());
        this.addSerializer(InputStreamSource.class, new LogObjectSerializer());
        this.addSerializer(File.class, new LogObjectSerializer());
    }
}
