package com.auto.boot.common.service;

import java.io.Serializable;

/**
 * 业务代码结果接口
 *
 * @author zhaohaifan
 */
public interface IResultCode extends Serializable {

    /**
     * 获取消息
     *
     * @return
     */
    String getMsg();

    /**
     * 获取状态码
     *
     * @return
     */
    int getCode();

}
