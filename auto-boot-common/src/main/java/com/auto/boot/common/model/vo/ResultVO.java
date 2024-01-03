package com.auto.boot.common.model.vo;


import com.auto.boot.common.enums.ResultEnum;
import com.auto.boot.common.service.IResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * 通用的返回值参数
 *
 * @author zhaohaifan
 */
@Getter
@Setter
@Builder
@ToString
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 8934156528968025889L;

    public static final Integer FAIL_CODE = 500;


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 承载数据
     */
    private T data;

    /**
     * 是否成功
     */
    private Boolean success;

    @Tolerate
    public ResultVO() {}

    /**
     * 成功
     * @param data 数据
     * @return 返回 Response
     */
    public static <T> ResultVO<T> success(T data) {
        return ResultVO.<T>builder()
                .data(data)
                .code(ResultEnum.SUCCESS.getCode())
                .msg(ResultEnum.SUCCESS.getMsg())
                .success(true)
                .build();
    }

    /**
     * 成功
     * @return 返回 Response
     */
    public static ResultVO<Void> success() {
        return ResultVO.<Void>builder()
                .code(ResultEnum.SUCCESS.getCode())
                .msg(ResultEnum.SUCCESS.getMsg())
                .success(true)
                .build();
    }

    /**
     * 成功
     *
     * @param msg 自定义响应描述信息
     * @return 返回 Response
     */
    public static ResultVO<Void> successCustomMsg(String msg) {
        return ResultVO.<Void>builder()
                .code(ResultEnum.SUCCESS.getCode())
                .msg(msg)
                .success(true)
                .build();
    }

    /**
     * 失败
     * @param resultCode 枚举
     * @return 返回 Response
     */
    public static <T> ResultVO<T> fail(IResultCode resultCode) {
        return ResultVO.<T>builder()
                .code(resultCode.getCode())
                .msg(resultCode.getMsg())
                .success(false)
                .build();
    }

    /**
     * 失败
     * @param code code值
     * @param msg 描述信息
     * @return 返回 Response
     */
    public static <T> ResultVO<T> fail(int code, String msg) {
        return ResultVO.<T>builder()
                .code(code)
                .msg(msg)
                .success(false)
                .build();
    }

    /**
     * 失败
     * @param resultCode 枚举
     * @param message 描述信息
     * @return 返回 Response
     */
    public static <T> ResultVO<T> fail(IResultCode resultCode, String message) {
        return ResultVO.<T>builder()
                .code(resultCode.getCode())
                .msg(message)
                .success(false)
                .build();
    }

    /**
     * 失败
     * @param message 描述信息
     * @return 返回 Response
     */
    public static <T> ResultVO<T> fail(String message) {
        return ResultVO.<T>builder()
                .code(FAIL_CODE)
                .msg(message)
                .success(false)
                .build();
    }

    /**
     * 自定义
     * @param code code值
     * @param msg 描述信息
     * @param data 数据
     * @return 返回 Response
     */
    public static <T> ResultVO<T> custom(int code, String msg, T data) {
        return ResultVO.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .success(code == ResultEnum.SUCCESS.getCode())
                .build();
    }

    /**
     * 是否成功
     *
     * @return 返回结果 true: 成功 false: 失败
     */
    public boolean isSuccess() {
        return code != null && ResultEnum.SUCCESS.getCode() == code;
    }
}
