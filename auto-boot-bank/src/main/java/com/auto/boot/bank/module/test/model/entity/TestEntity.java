package com.auto.boot.bank.module.test.model.entity;

import com.auto.boot.starter.mybatis.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString
@TableName("t_test")
public class TestEntity extends BaseEntity {

    @TableField("code")
    private String code;

    @TableField("user_id")
    private String userId;

    @TableField("name")
    private String name;

    @TableField("score")
    private BigDecimal score;
}
