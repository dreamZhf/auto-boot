package com.auto.boot.common.enums;

import com.auto.boot.common.service.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果枚举
 *
 * @author zhaohaifan
 */

@Getter
@AllArgsConstructor
public enum ResultEnum implements IResultCode {

    /**
     * 系统异常
     */
    BIZ_FAIL(500, "系统异常"),

    /**
     * 成功
     */
    SUCCESS(200, "成功"),


    /**
     * token不能为空
     */
    TOKEN_IS_EMPTY(10000, "token不能为空"),

    /**
     * 用户未登录或token已失效
     */
    TOKEN_INVALID(10001, "用户未登录或token已失效"),

    /**
     * 无权限
     */
    NOT_PERMISSION(10002, "抱歉，您暂无权限操作"),

    /**
     * 参数校验不通过
     */
    PARAM_VALID_FAIL(10003, "参数校验不通过"),

    /**
     * 缺少参数
     */
    PARAM_LACK_FAIL(10004, "缺少 {}"),

    /**
     * 文件下载失败
     */
    DOWNLOAD_FILE_FAIL(10005, "文件下载失败"),

    /**
     * 文件上传失败
     */
    UPLOAD_FILE_FAIL(10006, "文件上传失败"),

            ;
    final int code;
    final String msg;
}
