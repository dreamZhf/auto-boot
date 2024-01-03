package com.auto.boot.bank.module.test.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 测试 - 新增 - 请求参数
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString
public class TestAddDTO {

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空")
    private String code;

    /**
     * userId
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 分数
     */
    private BigDecimal score;
}
