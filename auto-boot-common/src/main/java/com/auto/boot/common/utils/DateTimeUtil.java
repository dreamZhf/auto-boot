package com.auto.boot.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author zhaohaifan
 */
public class DateTimeUtil {

    public static final String FORMATTER_1 = "yyyyMMdd";
    public static final String FORMATTER_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATTER_3 = "yyyy-MM-dd";
    public static final String FORMATTER_4 = "HH:mm:ss";
    public static final String FORMATTER_5 = "yyyy-MM";
    public static final String FORMATTER_6 = "yyyy年MM月dd日";
    public static final String FORMATTER_7 = "yyyy/MM/dd";
    public static final String FORMATTER_8 = "yyyy.MM.dd";
    public static final String FORMATTER_9 = "yyyyMM";
    public static final String FORMATTER_10 = "yyyy-MM";
    public static final String FORMATTER_11 = "yyMM";
    public static final String FORMATTER_12 = "MMyy";
    public static final String FORMATTER_13 = "MM/yy";
    public static final String FORMATTER_14 = "HHmmss";
    public static final String FORMATTER_15 = "HH时mm分ss秒";
    public static final String FORMATTER_16 = "HHmmssSSS";
    public static final String FORMATTER_17 = "HH:mm";
    public static final String FORMATTER_18 = "yyyyMMddHHmmss";
    public static final String FORMATTER_19 = "yyyyMMddHHmmssSSS";
    public static final String FORMATTER_20 = "yyyyMMddHHmmssSS";
    public static final int ONE_MINUTE_SECONDS = 60;
    public static final int ONE_HOUR_SECONDS = 3600;
    public static final int ONE_DAY_SECONDS = 86400;
    public static final int ONE_SECONDS_MILLIS = 1000;


    public static final DateTimeFormatter DATETIME_FORMATTER_1 = DateTimeFormatter.ofPattern(FORMATTER_1);
    public static final DateTimeFormatter DATETIME_FORMATTER_2 = DateTimeFormatter.ofPattern(FORMATTER_2);
    public static final DateTimeFormatter DATETIME_FORMATTER_3 = DateTimeFormatter.ofPattern(FORMATTER_3);
    public static final DateTimeFormatter DATETIME_FORMATTER_4 = DateTimeFormatter.ofPattern(FORMATTER_4);

    private DateTimeUtil() {}

    /**
     * 给某个日期加上天数 并获取到该日期的最大时间
     * 例: localDate = 2023-04-01, day = 5, 最后返回时间为 2023-04-06 23:59:59
     *
     * @param localDate 日期
     * @param day 增加的天数
     * @return 返回增加后的日期最大时间
     */
    public static String addDayGetMaxTimeStr(LocalDate localDate, int day) {
        LocalDateTime localDateTime = addDayGetMaxTime(localDate, day);
        return DATETIME_FORMATTER_2.format(localDateTime);
    }

    /**
     * 给某个日期加上天数 并获取到该日期的最大时间
     * 例: localDate = 2023-04-01, day = 5, 最后返回时间为 2023-04-06 23:59:59
     *
     * @param localDate 日期
     * @param day 增加的天数
     * @return 返回增加后的日期最大时间
     */
    public static LocalDateTime addDayGetMaxTime(LocalDate localDate, int day) {
        return LocalDateTime.of(localDate.plusDays(day), LocalTime.MAX);
    }

    /**
     * 转换格式
     *
     * @param dateTime 时间
     * @param beforeFormat 转换前格式
     * @param afterFormat 转换后格式
     * @return 返回转换后的时间
     */
    public static String formatDateTime(String dateTime, String beforeFormat, String afterFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(beforeFormat);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        return DateTimeFormatter.ofPattern(afterFormat).format(localDateTime);
    }

    /**
     * 格式化时间
     *
     * @param localDateTime 时间
     * @param formatter 格式
     * @return 返回结果
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return formatter.format(localDateTime);
    }

    /**
     * 格式化时间
     *
     * @param localDateTime 时间
     * @param format        格式
     * @return 返回格式化后的字符串
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return null;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return fmt.format(localDateTime);
    }

    /**
     * 字符串 转 时间
     *
     * @param str    字符串
     * @param formatter 格式
     * @return LocalDateTime
     */
    public static LocalDateTime strToLocalDateTime(String str, DateTimeFormatter formatter) {
        return LocalDateTime.parse(str, formatter);
    }

    /**
     * 时间戳 转 LocalDateTime
     *
     * @param timestamp 时间戳
     * @return 返回 LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * LocalDate 转 String
     *
     * @param date 日期
     * @return 返回 String
     */
    public static String LocalDateToStr(LocalDate date) {
        return date.format(DATETIME_FORMATTER_3);
    }

    /**
     * String 转 LocalDate
     *
     * @param dateStr 日期Str
     * @return 返回 LocalDate
     */
    public static LocalDate strToLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATETIME_FORMATTER_3);
    }

    /**
     * LocalDateTime 转 LocalDate
     *
     * @param localDateTime 日期时间
     * @return 返回 LocalDate
     */
    public static LocalDate LocalDateTimeToLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    /**
     * LocalDate 转 LocalDateTime
     *
     * @param localDate 日期
     * @return 返回 LocalDateTime
     */
    public static LocalDateTime LocalDateToLocalDateTime(LocalDate localDate) {
        // 当前时间时分秒
        LocalTime localTimeNow = LocalTime.now();
        return LocalDateTime.of(localDate, localTimeNow);
    }

    /**
     * 获取指定天数后的日期
     *
     * @param dateStr 日期
     * @return 返回 LocalDate
     */
    public static LocalDate addDay(String dateStr,Long days) {
        LocalDate parse = LocalDate.parse(dateStr, DATETIME_FORMATTER_3);
        return parse.plusDays(days);
    }

    /**
     * 获取指定月数后的日期
     *
     * @param dateStr 日期
     * @return 返回 LocalDate
     */
    public static LocalDate addMonth(String dateStr,Long months) {
        LocalDate parse = LocalDate.parse(dateStr, DATETIME_FORMATTER_3);
        return parse.plusMonths(months);
    }

    /**
     * 获取指定年数后的日期
     *
     * @param dateStr 日期
     * @return 返回 LocalDate
     */
    public static LocalDate addYear(String dateStr,Long years) {
        LocalDate parse = LocalDate.parse(dateStr, DATETIME_FORMATTER_3);
        return parse.plusYears(years);
    }

    /**
     * 获取指定周数后的日期
     *
     * @param dateStr 日期
     * @return 返回 LocalDate
     */
    public static LocalDate addWeek(String dateStr,Long weeks) {
        LocalDate parse = LocalDate.parse(dateStr, DATETIME_FORMATTER_3);
        return parse.plusWeeks(weeks);
    }

    /**
     * date 转 LocalDateTime
     *
     * @param date 时间
     * @return 返回 LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 日期减少时间量
     *
     * @param date   时间
     * @param amount 时间量
     * @return 设置后的时间
     */
    public static Date minus(Date date, TemporalAmount amount) {
        Instant instant = date.toInstant();
        return Date.from(instant.minus(amount));
    }

    /**
     * 减少天
     *
     * @param date 时间
     * @param days 减少的天数
     * @return 设置后的时间
     */
    public static Date minusDays(Date date, long days) {
        return minus(date, Duration.ofDays(days));
    }

    /**
     * 日期格式化
     *
     * @param date    时间
     * @param pattern 表达式
     * @return 格式化后的时间
     */
    public static String format(Date date, String pattern) {
        return formatLocalDateTime(dateToLocalDateTime(date), pattern);
    }
}
