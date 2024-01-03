package com.auto.boot.common.exception;

/**
 * 业务异常 - 不需要事务回滚
 *
 * @author zhaohaifan
 */
public class ServiceNotRollbackException extends ServiceException {

    private static final long serialVersionUID = -8356302557395277712L;

    public ServiceNotRollbackException(String message) {
        super(message);
    }
}
