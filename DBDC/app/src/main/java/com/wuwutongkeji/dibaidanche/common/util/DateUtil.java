package com.wuwutongkeji.dibaidanche.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mr.Bai on 17/9/15.
 */

public class DateUtil {

    public static final String yyyy_MM_dd_HH__mm__ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";


    /**
     * string 转时间戳
     * @param time
     * @param format
     * @return
     */
    public static long String2Long(String time,String format){
        try {
            SimpleDateFormat sf  = new SimpleDateFormat(format);
            return sf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间戳转string
     * @param time
     * @param format
     * @return
     */
    public static String long2String(long time,String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format);
        java.util.Date date = new Date(time);
        return sdf.format(date);
    }

    /***
     * 时间格式化
     * @param time
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String dateFormat(String time,String oldFormat,String newFormat){
        return long2String(String2Long(time,oldFormat),newFormat);
    }

    /**
     * 时间错转 时分秒
     * @param l
     * @return
     */
    public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute)  + ":"  + getTwoLength(second));
    }

    /***
     * 空位补0
     * @param data
     * @return
     */
    public static String getTwoLength(final int data) {
        if(data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    /***
     * 获取 0点0分0秒的时间
     * @return
     */
    public static long getTodayStartDate(){
        long current = System.currentTimeMillis();
        return current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
    }

    /**
     * long 转天数
     * @param time
     * @return
     */
    public static long long2Day(long time){
        return time / (3600*24*1000);
    }
}
