package com.auto.boot.common.exception;

import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.model.vo.ResultVO;
import com.auto.boot.common.service.IResultCode;

/**
 * 服务异常
 *
 * @author zhaohaifan
 */
public class ServiceException extends AutoException {

    private static final long serialVersionUID = 4107335971709935238L;

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultEnum.BIZ_FAIL;
    }

    public ServiceException(IResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ServiceException(IResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    public ServiceException(IResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
    public ServiceException(IResultCode resultCode, String message, Throwable cause) {
        super(message,cause);
        this.resultCode = resultCode;
    }

    public int getCode() {
        return resultCode.getCode();
    }

    /**
     * 适用于远程调用时的异常
     * @param r
     */
    public ServiceException(ResultVO<?> r) {
        super(r.getMsg());
        this.resultCode = new IResultCode() {
            @Override
            public String getMsg() {
                return r.getMsg();
            }

            @Override
            public int getCode() {
                return r.getCode();
            }
        };
    }

    /**
     * 适用于远程调用时的异常
     */
    public ServiceException(String msg, int code) {
        super(msg);
        this.resultCode = new IResultCode() {
            private static final long serialVersionUID = -5696264666768909985L;

            @Override
            public String getMsg() {
                return msg;
            }

            @Override
            public int getCode() {
                return code;
            }
        };
    }
}
