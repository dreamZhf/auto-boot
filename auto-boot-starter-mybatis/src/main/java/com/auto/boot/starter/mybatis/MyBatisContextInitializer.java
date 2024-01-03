package com.auto.boot.starter.mybatis;

import com.auto.boot.common.utils.JsonUtil;
import com.auto.boot.starter.common.utils.AutoLogUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

/**
 * mybatis 配置初始化
 *
 * @author zhaohaifan
 */
@Slf4j
public class MyBatisContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        if (ContextInitializerFlag.myBatisContextInitializer) {
            return;
        }
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("mybatis.mapper-locations", "classpath:mapper/*.xml");
        map.put("spring.datasource.druid.initialSize", 10);
        map.put("spring.datasource.druid.minIdle", 1);
        map.put("spring.datasource.druid.maxActive", 100);
        map.put("spring.datasource.druid.maxWait", 10000);
        map.put("spring.datasource.druid.timeBetweenEvictionRunsMillis", 60000);
        map.put("spring.datasource.druid.minEvictableIdleTimeMillis", 300000);
        map.put("spring.datasource.druid.testWhileIdle", true);
        map.put("spring.datasource.druid.testOnBorrow", false);
        map.put("spring.datasource.druid.testOnReturn", false);
        map.put("spring.datasource.druid.defaultAutoCommit", true);
        map.put("spring.datasource.druid.validationQuery", "select 1");
        map.put("spring.datasource.druid.poolPreparedStatements", true);
        map.put("spring.datasource.druid.maxOpenPreparedStatements", 20);
        map.put("spring.datasource.druid.filters", "stat,wall");
        map.put("spring.datasource.druid.filter.stat.log-slow-sql", true);
        map.put("spring.datasource.druid.filter.stat.merge-sql", true);
        map.put("spring.datasource.druid.filter.stat.slow-sql-millis", 2000);

        MapPropertySource propertySource = new MapPropertySource("MyBatisContextInitializer", map);
        environment.getPropertySources().addLast(propertySource);
        log.info("初始化mybatis参数：{}", AutoLogUtil.toLogJson(map));
        ContextInitializerFlag.myBatisContextInitializer = true;
    }
}
