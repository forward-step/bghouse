package com.supyp.bghouse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/*
* 日期转换工具
* */
public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // 输出当前时间 eg. 2020-11-11 10:41:00
    public static Date LocalDateAndTime(){
        return new Date(System.currentTimeMillis());
    }
    // 将String 转换为 Date对象
    public static Date string2Date(String date) throws ParseException {
        return sdf.parse(date);
    }
    public static String date2String(Date date){
        return sdf.format(date);
    }
    public static String date2StringByYearAndMonth(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }


    // 判断Date是否已经过期
    public static Boolean isExpire(int expireTime,Date date){
        // 当前时间戳 - date > expireTime就是过期了
        return System.currentTimeMillis() - date.getTime() > expireTime;
    }

    // JDK8将Date对象转换为LocalDateTime
    public static LocalDateTime date2LocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }
    public static Date localDateTime2Date( LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);//Combines this date-time with a time-zone to create a  ZonedDateTime.
        return Date.from(zdt.toInstant());
    }

    // 计算俩个日期之间相差的月份
    public static int getMonthDiff(Date largeDate, Date smallDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(largeDate);
        c2.setTime(smallDate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值
        int yearInterval = year1 - year2;
        // 获取月数差值
        int monthInterval = month1 - month2;
        // 获取日份差距
        int dayInterval = 0;
        if(day1 < day2){
            dayInterval = -1;
        }
        return yearInterval * 12 + monthInterval + dayInterval;
    }

    // 获取过去第几天的日期
    public static String getPassDate(int pass){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - pass);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    // 获取未来第几天的日期
    public static String getFetureDate(int pass) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + pass);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }
}
