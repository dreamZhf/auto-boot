package com.auto.boot.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拼音工具类
 *
 * @author zhaohaifan
 */
@Slf4j
public class PinyinUtil {
	
	/**
     *  获取汉字首字母或全拼大写字母
     *
     * @param chinese 汉字
     * @param isFull  是否全拼 true:表示全拼 false表示：首字母
     *
     * @return 全拼或者首字母大写字符串
     */
    public static String getUpperCase(String chinese,boolean isFull){
        return convertHanZi2Pinyin(chinese,isFull).toUpperCase();
    }

    /**
     * 获取汉字首字母或全拼小写字母
     *
     * @param chinese 汉字
     * @param isFull 是否全拼 true:表示全拼 false表示：首字母
     *
     * @return 全拼或者首字母小写字符窜
     */
    public static  String getLowerCase(String chinese,boolean isFull){
        return convertHanZi2Pinyin(chinese,isFull).toLowerCase();
    }

    /**
     * 将汉字转成拼音
     * <P>
     * 取首字母或全拼
     *
     * @param hanZi 汉字字符串
     * @param isFull 是否全拼 true:表示全拼 false表示：首字母
     *
     * @return 拼音
     */
    private static String convertHanZi2Pinyin(String hanZi,boolean isFull){

        /**
         * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
         * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
         * ^[\u4E00-\u9FA5]+$ 匹配简体
         */
        String regExp="^[\u4E00-\u9FFF]+$";
        StringBuilder sb = new StringBuilder();
        if(hanZi == null || "".equals(hanZi.trim())) {
            return "";
        }
        String pinyin="";
        for(int i=0; i<hanZi.length(); i++){
            char unit = hanZi.charAt(i);
            //是汉字，则转拼音
            if(match(String.valueOf(unit),regExp)){
                pinyin=convertSingleHanzi2Pinyin(unit);
                if(isFull){
                    sb.append(pinyin);
                }
                else{
                    sb.append(pinyin.charAt(0));
                }
            }else{
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    /**
     * 将单个汉字转成拼音
     *
     * @param hanzi 汉字字符
     *
     * @return 拼音
     */
    private static String convertSingleHanzi2Pinyin(char hanzi){
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        try {
            StringBuilder sb = new StringBuilder();
            res = PinyinHelper.toHanyuPinyinStringArray(hanzi,outputFormat);
            // 对于多音字，只用第一个拼音
            sb.append(res[0]);
            return sb.toString();
        } catch (Exception e) {
            log.error("将单个汉字转成拼音 异常", e);
            return "";
        }
    }

    /***
     * 匹配
     * <P>
     * 根据字符和正则表达式进行匹配
     *
     * @param str 源字符串
     * @param regex 正则表达式
     *
     * @return true：匹配成功  false：匹配失败
     */
    private static boolean match(String str,String regex){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }
}
