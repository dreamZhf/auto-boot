package com.auto.boot.starter.common.filter;

import com.auto.boot.starter.common.properties.AutoProperties;
import lombok.AllArgsConstructor;

/**
 * @author zhaohaifan
 */
@AllArgsConstructor
public abstract class AbstractFilter implements IFilter {

    protected final AutoProperties autoProperties;
}
