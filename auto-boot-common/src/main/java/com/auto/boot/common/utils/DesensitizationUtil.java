package com.auto.boot.common.utils;

import cn.aos.isigning.autograph.autographcommon.constant.StringPool;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 脱敏工具类, 根据 https://aosign.yuque.com/qbhxe1/rics5c/notg5k40s8zik100?singleDoc# 此文档需求
 *
 * @author zhaohaifan
 */
public class DesensitizationUtil {

    private DesensitizationUtil() {}

    /**
     * 姓名脱敏
     * 去掉首位
     *
     * 脱敏前示例:
     *      张三
     *      吴彦祖
     *      慕容景岳
     *
     *  脱敏后示例:
     *      *三
     *      *彦祖
     *      *容景岳
     * @param str 姓名
     * @return 返回脱敏值
     */
    public static String realName(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String s = str.substring(1);
        return StringUtil.leftPad(s, str.length(), "*");
    }

    /**
     * 手机号脱敏
     * 保留前 3 位和后 4 位
     *
     * 脱敏前示例:
     *      18523544116
     * 脱敏后示例:
     *      185****4116
     *
     * @param str 手机号
     * @return 返回脱敏值
     */
    public static String mobile(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String before = StringUtil.left(str, 3);
        String after = StringUtil.right(str, 4);
        return StringUtil.rightPad(before, str.length() - 4, "*") + after;
    }

    /**
     * 电话脱敏
     * 区号不脱敏 号码部分保留前 2 位和后 2 位
     * 中间用4个*号填充
     *
     * 脱敏前示例:
     *      (023)54321234
     * 脱敏后示例:
     *      (023)54****34
     *
     * @param str 手机号
     * @return 返回脱敏值
     */
    public static String phone(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String s = str;
        String areaCode = "";
        if (str.contains(StringPool.LEFT_BRACKET) || str.contains(StringPool.CN_LEFT_BRACKET)) {
            // 包含中英文括号说明有区号
            int index = str.indexOf(StringPool.RIGHT_BRACKET);
            index = index == -1 ? str.indexOf(StringPool.CN_RIGHT_BRACKET) : index;
            if (index != -1) {
                s = str.substring(index + 1);
            }
            areaCode = str.substring(0, index + 1);
        }

        String before = StringUtil.left(s, 2);
        String after = StringUtil.right(s, 2);
        return areaCode + StringUtil.rightPad(before, before.length() + 4, "*") + after;
    }

    /**
     * 身份证号脱敏
     * 保留身份证号前 3 位与后 3 位
     * 中间用6个*号填充
     *
     * 脱敏前示例:
     *      110225196403026127
     *      110223790813697
     * 脱敏后示例:
     *      110******127
     *      110*********697
     *
     * @param str 身份证号
     * @return 返回脱敏值
     */
    public static String idCard(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String before = StringUtil.left(str, 3);
        String after = StringUtil.right(str, 3);
        return StringUtil.rightPad(before, before.length() + 6, "*") + after;
    }

    /**
     * 组织机构名称 脱敏
     * 保留前 2 位和后 4 位
     * 中间用6个*号填充
     *
     * 脱敏前示例:
     *      重庆傲雄在线信息技术有限公司
     * 脱敏后示例:
     *      重庆******有限公司
     *
     * @param str 组织机构名称
     * @return 返回脱敏值
     */
    public static String enterpriseName(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        int mixLength = 6;
        if (str.length() <= mixLength) {
            return str;
        }
        String before = StringUtil.left(str, 2);
        String after = StringUtil.right(str, 4);
        return StringUtil.rightPad(before, before.length() + 6, "*") + after;
    }

    /**
     * 统一社会信用代码 脱敏
     * 抹掉后8~5位
     *
     * 脱敏前示例:
     *      91500108759283982R
     * 脱敏后示例:
     *      9150010875****982R
     *
     * @param str 统一社会信用代码
     * @return 返回脱敏值
     */
    public static String socialCreditCode(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        int mixLength = 6;
        if (str.length() <= mixLength) {
            return str;
        }
        // TODO: 2023/6/20 待处理
        String before = StringUtil.left(str, 2);
        String after = StringUtil.right(str, 4);
        return StringUtil.rightPad(before, before.length() + 6, "*") + after;
    }

    /**
     * 组织机构代码 脱敏
     * 保留前 3 位和后 3 位
     *
     * 脱敏前示例:
     *      75928398-2
     * 脱敏后示例:
     *      759****8-2
     *
     * @param str 组织机构代码
     * @return 返回脱敏值
     */
    public static String organizationCode(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String before = StringUtil.left(str, 3);
        String after = StringUtil.right(str, 3);
        return StringUtil.rightPad(before, str.length() - 3, "*") + after;
    }

    /**
     * 工商注册号 脱敏
     * 保留前 3 位和后 3 位
     *
     * 脱敏前示例:
     *      500108000083801
     * 脱敏后示例:
     *      500*********801
     *
     * @param str 工商注册号
     * @return 返回脱敏值
     */
    public static String businessRegistrationNumber(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String before = StringUtil.left(str, 3);
        String after = StringUtil.right(str, 3);
        return StringUtil.rightPad(before, str.length() - 3, "*") + after;
    }

    /**
     * IPv4地址 脱敏
     * 保留首段 和 末段
     * 中间位非.号 用*填充3位
     *
     * 脱敏前示例:
     *      14.111.231.98
     * 脱敏后示例:
     *      14.***.***.98
     *
     * @param str IPv4地址
     * @return 返回脱敏值
     */
    public static String ipv4(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String before = StringUtil.left(str, str.indexOf("."));
        String after = StringUtil.substring(str, str.lastIndexOf(".") + 1);
        String asterisk = "***";
        return before + StringPool.DOT + asterisk + StringPool.DOT + asterisk + StringPool.DOT + after;
    }

    /**
     * IPv6地址 脱敏
     * 保留首段 和 末段
     * 中间位非:号 用*填充4位
     *
     * 脱敏前示例:
     *      2404:c801:e53:8476:9834:f4ff:fe88:a4
     * 脱敏后示例:
     *      2404:****:***:****:****:****:a4
     *
     * @param str IPv6地址
     * @return 返回脱敏值
     */
    public static String ipv6(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        String[] strings = str.split(StringPool.COLON);
        List<String> stringList = Lists.newArrayList();
        String asterisk = "****";
        for (int i = 0;i < strings.length; i++) {
            if (i == 0) {
                stringList.add(strings[i]);
            } else if (i == strings.length - 1){
                stringList.add(strings[i]);
            } else {
                stringList.add(asterisk);
            }
        }
        return StringUtil.join(stringList, StringPool.COLON);
    }

    /**
     * 地址信息类 脱敏
     * 省市区信息不处理
     * 地址明细保留前4位，后1位
     *
     * 脱敏前示例:
     *      重庆市渝北区金开大道西段106号13幢17-1号
     * 脱敏后示例:
     *      重庆市渝北区金开大道*************号
     *
     * @param str IPv6地址
     * @return 返回脱敏值
     */
    public static String address(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        // TODO: 2023/6/8 不好处理，逻辑待定
        return str;
    }
}
