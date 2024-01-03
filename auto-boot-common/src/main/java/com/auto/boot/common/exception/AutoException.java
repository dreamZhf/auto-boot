package com.auto.boot.common.exception;

import com.auto.boot.common.service.IResultCode;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author zhaohaifan
 */
public class AutoException extends RuntimeException {

    private static final long serialVersionUID = 4837912984104885578L;

    /**
     * 枚举
     */
    @Getter
    protected IResultCode resultCode;

    public AutoException() {
    }

    public AutoException(String message) {
        super(message);
    }

    public AutoException(Throwable cause) {
        super(cause);
    }

    public AutoException(String message, Throwable cause) {
        super(message,cause);
    }
}
