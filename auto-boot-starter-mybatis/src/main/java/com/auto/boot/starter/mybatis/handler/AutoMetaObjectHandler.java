package com.auto.boot.starter.mybatis.handler;

import com.auto.boot.starter.common.subject.Subject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动填充当前登录用户及时间
 *
 * @author zhaohaifan
 */
@Slf4j
public class AutoMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String CREATE_USER_ID = "createUserId";
    private static final String UPDATE_TIME = "updateTime";
    private static final String UPDATE_USER_ID = "updateUserId";

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = Subject.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        if (getFieldValByName(CREATE_USER_ID, metaObject) == null) {
            setFieldValByName(CREATE_USER_ID, userId, metaObject);
        }
        if (getFieldValByName(UPDATE_USER_ID, metaObject) == null) {
            setFieldValByName(UPDATE_USER_ID, userId, metaObject);
        }
        if (getFieldValByName(CREATE_TIME, metaObject) == null) {
            setFieldValByName(CREATE_TIME, now, metaObject);
        }
        if (getFieldValByName(UPDATE_TIME, metaObject) == null) {
            setFieldValByName(UPDATE_TIME, now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (getFieldValByName(UPDATE_USER_ID, metaObject) == null) {
            setFieldValByName(UPDATE_USER_ID, Subject.getCurrentUserId(), metaObject);
        }
        if (getFieldValByName(UPDATE_TIME, metaObject) == null) {
            setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        }
    }
}
