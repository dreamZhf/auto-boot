package com.auto.boot.common.enums;

import lombok.Getter;

/**
 * 是与否枚举
 * @author dream
 */
@Getter
public enum YesOrNoEnum {

    /**
     * 否
     */
    NO(0, "否"),

    /**
     * 是
     */
    YES(1, "是"),
    ;

    /**
     * code 码
     */
    private final int code;

    /**
     * 描述信息
     */
    private final String msg;

    YesOrNoEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据 code 获取枚举
     * @param code code码
     * @return 返回枚举
     */
    public static YesOrNoEnum getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        YesOrNoEnum yesOrNoEnum = null;
        YesOrNoEnum[] yesOrNoEnums = YesOrNoEnum.values();
        for (YesOrNoEnum e : yesOrNoEnums) {
            if (e.getCode() == code) {
                yesOrNoEnum = e;
                break;
            }
        }
        return yesOrNoEnum;
    }
}
