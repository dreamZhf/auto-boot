package com.auto.boot.starter.common;

import com.auto.boot.starter.common.utils.SpringBeanUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.TtlMDCAdapter;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

/**
 * 工具 自动配置
 *
 * @author zhaohaifan
 */
@Slf4j
@AllArgsConstructor
public class AutoBootUtilAutoConfiguration {

    private final ApplicationContext applicationContext;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        TtlMDCAdapter.init();
        SpringBeanUtil.setApplicationContext(applicationContext);
    }
}
