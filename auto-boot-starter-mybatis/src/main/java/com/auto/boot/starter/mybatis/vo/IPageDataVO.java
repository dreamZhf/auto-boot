package com.auto.boot.starter.mybatis.vo;

import com.auto.boot.common.model.vo.PageDataVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页数据VO
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@ToString(callSuper = true)
public class IPageDataVO<T> extends PageDataVO<T> {

    public IPageDataVO(IPage<T> page) {
        this.setTotal(page.getTotal());
        this.setPage(page.getCurrent());
        this.setSize(page.getSize());
        this.setRecords(page.getRecords());
    }

    public IPageDataVO(IPage<?> page, List<T> records) {
        this.setTotal(page.getTotal());
        this.setPage(page.getCurrent());
        this.setSize(page.getSize());
        this.setRecords(records);
    }
}
