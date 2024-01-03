package com.auto.boot.bank.module.test.service;

import com.auto.boot.bank.module.test.model.dto.TestAddDTO;

/**
 * @author zhaohaifan
 */
public interface TestService {

    /**
     * 新增测试
     *
     * @param addDTO
     */
    void addTest(TestAddDTO addDTO);
}
