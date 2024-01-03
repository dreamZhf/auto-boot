package com.auto.boot.starter.common.exception;

import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.exception.BizException;
import com.auto.boot.common.exception.ServiceException;
import com.auto.boot.common.exception.ServiceNotRollbackException;
import com.auto.boot.common.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 默认全局异常处理
 *
 * @author zhaohaifan
 */
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandler implements IGlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVO<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                           HttpServletRequest request) {
        log.warn("请求地址: {}, 不支持: {} 请求", request.getRequestURI(), e.getMethod());
        return ResultVO.fail(ResultEnum.BIZ_FAIL, e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVO<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.error("请求地址: {}, 发生未知异常.", request.getRequestURI(), e);
        return ResultVO.fail(ResultEnum.BIZ_FAIL);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVO<?> handleException(Exception e, HttpServletRequest request) {
        log.error("请求地址: {},发生系统异常.", request.getRequestURI(), e);
        return ResultVO.fail(ResultEnum.BIZ_FAIL);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResultVO<?> handleBindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        String msg = ResultEnum.PARAM_VALID_FAIL.getMsg();
        if (fieldError != null) {
            msg = fieldError.getDefaultMessage();
            log.warn("校验异常, 字段名为: {}, 错误信息为: {}", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            log.warn("校验异常, 但未获取到异常信息: {}", e.getMessage());
        }
        return ResultVO.fail(ResultEnum.PARAM_VALID_FAIL, msg);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ValidationException.class)
    public ResultVO<?> handleValidationException(ValidationException e) {
        log.warn("校验异常, 错误信息为: {}", e.getMessage());
        return ResultVO.fail(ResultEnum.PARAM_VALID_FAIL, e.getMessage());
    }

    /**
     * 业务异常
     * @param e 业务异常
     * @return 返回 Response
     */
    @ExceptionHandler(BizException.class)
    public ResultVO<?> handleBizException(BizException e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        log.warn("bizException, 错误编码为: {}, 错误信息为: {}, 异常类: {}", e.getCode(), e.getMessage(),
                stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber());
        return ResultVO.custom(e.getCode(), e.getMessage(), e.getData());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResultVO<?> handleServiceException(ServiceException e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        log.warn("ServiceException, 错误编码为: {}, 错误信息为: {}, 异常类: {}", e.getCode(), e.getMessage(),
                stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber());
        String message = e.getMessage();
        return ResultVO.fail(e.getResultCode().getCode(), message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ServiceNotRollbackException.class)
    public ResultVO<?> handleServiceException(ServiceNotRollbackException e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        log.warn("ServiceNotRollbackException, 错误编码为: {}, 错误信息为: {}, 异常类: {}", e.getCode(), e.getMessage(),
                stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber());
        String message = e.getMessage();
        return ResultVO.fail(e.getResultCode().getCode(), message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String msg = ResultEnum.PARAM_VALID_FAIL.getMsg();
        if (fieldError != null) {
            msg = fieldError.getDefaultMessage();
            log.warn("校验异常, 字段名为: {}, 错误信息为: {}", fieldError.getField(), fieldError.getDefaultMessage());
        } else {
            log.warn("校验异常, 但未获取到异常信息: {}", e.getMessage());
        }
        return ResultVO.fail(ResultEnum.PARAM_VALID_FAIL, msg);
    }

    /**
     * 必要参数缺少
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultVO<?> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("缺少: {} 参数, msg: {}", e.getParameterName(), e.getMessage());
        return ResultVO.fail(ResultEnum.PARAM_LACK_FAIL, e.getParameterName());
    }
}
