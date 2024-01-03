package com.auto.boot.common.model.vo;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页数据VO
 * @author dream
 */
@Getter
@Setter
@ToString
public class PageDataVO<T> {

    /**
     * 总数量
     */
    private Long total = 0L;

    /**
     * 当前页
     */
    private Long page;

    /**
     * 每页条数
     */
    private Long size;

    /**
     * 数据列表
     */
    private List<T> records;

    public PageDataVO() {
        this.records = Lists.newArrayList();
    }
}
