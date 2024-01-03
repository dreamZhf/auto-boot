package com.auto.boot.common.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 基础分页DTO
 * @author dream
 */
@Getter
@Setter
@ToString
public class BasePageDTO {

    /**
     * 当前页
     */
    @NotNull(message = "页码不能为空")
    private Long page;

    /**
     * 每页数量
     */
    @NotNull(message = "每页数量不能为空")
    private Long size;

    /**
     * 默认页码
     */
    private static final long DEFAULT_PAGE = 1;

    /**
     * 默认每页数量
     */
    private static final long DEFAULT_PAGE_SIZE = 10;

    public BasePageDTO() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_PAGE_SIZE;
    }
}
