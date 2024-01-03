package com.auto.boot.common.exception;

import com.auto.boot.common.service.IResultCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

/**
 * 业务异常
 *
 * @author dream
 */
public class BizException extends AutoException {

    private static final long serialVersionUID = 8303395350137669556L;

    /**
     * 描述信息
     */
    private String msg;

    /**
     * code 码
     */
    private Integer code;

    /**
     * 参数列表
     */
    private Object[] params;

    /**
     * 承载数据
     */
    private Object data;

    /**
     * 占位符
     */
    private static final String PLACEHOLDER = "{}";

    public BizException(IResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BizException(IResultCode resultCode, String msg) {
        this.msg = msg;
        this.resultCode = resultCode;
    }

    public BizException(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public BizException(IResultCode resultCode, Object ...params) {
        this.resultCode = resultCode;
        this.params = params;
    }

    @Override
    public String getMessage() {
        if (resultCode == null) {
            return msg;
        }
        String message = resultCode.getMsg();
        if (StringUtils.isBlank(msg) && params == null) {
            return message;
        }
        if (!message.contains(PLACEHOLDER) && StringUtils.isNotBlank(msg)) {
            // 不需要替换占位符，并且自定义信息不为空
            return msg;
        }
        if (message.contains(PLACEHOLDER) && StringUtils.isNotBlank(msg)) {
            // 需要替换占位符
            return MessageFormatter.format(message, msg).getMessage();
        }
        if (message.contains(PLACEHOLDER) && params != null) {
            return MessageFormatter.arrayFormat(message, params).getMessage();
        }
        return message;
    }

    /**
     * 获取异常编码
     * @return 返回异常编码
     */
    public Integer getCode() {
        return resultCode == null ? code : resultCode.getCode();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "异常编码: " +  getCode() + " , 异常描述: " + getMessage();
    }
}
