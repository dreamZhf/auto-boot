package com.auto.boot.bank.module.test.service.impl;

import com.auto.boot.bank.module.test.dms.TestDms;
import com.auto.boot.bank.module.test.model.dto.TestAddDTO;
import com.auto.boot.bank.module.test.model.entity.TestEntity;
import com.auto.boot.bank.module.test.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zhaohaifan
 */
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDms testDms;

    @Override
    public void addTest(TestAddDTO addDTO) {
        TestEntity entity = new TestEntity();
        entity.setCode(addDTO.getCode());
        entity.setName(addDTO.getName());
        entity.setUserId(addDTO.getUserId());
        entity.setScore(addDTO.getScore());
        testDms.save(entity);
    }
}
