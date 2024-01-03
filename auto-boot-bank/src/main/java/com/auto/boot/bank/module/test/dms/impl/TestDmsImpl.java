package com.auto.boot.bank.module.test.dms.impl;

import com.auto.boot.bank.module.test.dms.TestDms;
import com.auto.boot.bank.module.test.mapper.TestMapper;
import com.auto.boot.bank.module.test.model.entity.TestEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhaohaifan
 */
@Service
public class TestDmsImpl extends ServiceImpl<TestMapper, TestEntity> implements TestDms {

}
