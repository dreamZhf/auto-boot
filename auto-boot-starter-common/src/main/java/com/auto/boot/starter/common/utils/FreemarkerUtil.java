package com.auto.boot.starter.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;

/**
 * freemarker 工具类
 *
 * @author zhaohaifan
 */
@Slf4j
public class FreemarkerUtil {

    private FreemarkerUtil() {}

    /**
     * 生成文件
     *
     * @param templateFileName 模板文件名
     * @param data 数据内容
     * @param basePackagePath 基础路径
     * @return 返回内容
     */
    public static String generalFile(String templateFileName, Object data, String basePackagePath) throws IOException,
            TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/templates");
        Template template = configuration.getTemplate(templateFileName);
        StringWriter stringWriter = new StringWriter();
        template.process(data, stringWriter);
        return stringWriter.toString();
    }

    /**
     * 生成文件
     *
     * @param templateFileName 模板文件名
     * @param data 数据内容
     * @return 返回内容
     */
    public static String generalFile(String templateFileName, Object data) throws IOException, TemplateException {
        return generalFile(templateFileName, data, "/templates");
    }
}
