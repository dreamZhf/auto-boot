package com.auto.boot.starter.mybatis;

import com.auto.boot.starter.mybatis.utils.BizExecuteUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

/**
 * mybatis 工具 自动配置
 *
 * @author zhaohaifan
 */
@Slf4j
@AllArgsConstructor
public class MybatisUtilAutoConfiguration {

    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        log.info("init MybatisUtilAutoConfiguration");
        BizExecuteUtil.setPlatformTransactionManager(platformTransactionManager);
    }
}
