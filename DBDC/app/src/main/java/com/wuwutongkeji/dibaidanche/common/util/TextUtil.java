package com.wuwutongkeji.dibaidanche.common.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mr.Bai on 2016/4/13.
 */
public class TextUtil {

    /**
     * 是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    /****
     * 检查手机号
     *
     * 正确返回 true  ， 错误返回 false
     */
    public static boolean isCheckPhone(String mobile){
        String pattern = "^1(3|4|5|7|8)\\d{9}$";

        return mobile.matches(pattern);
    }

    public static String getThumbPhone(String phone){
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    public static boolean checkNikeName(String nikeName){
        String pattern = "[\\u4e00-\\u9fa5a-zA-Z0-9\\-\\_]{1,16}";
        return nikeName.matches(pattern);
    }

    /***
     * 分转换元
     * @param penny
     * @return
     */
    public static String getMoneyByPenny(long penny){
        return getMoneyByPenny(penny,"%.2f");
    }

    public static String getMoneyByPenny(long penny,String patten){
        return String.format(patten,penny / 100f);
    }
    /**
     * 15位和18位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    public static boolean isIdcard(String idcard) {
        return is15Idcard(idcard) || is18Idcard(idcard);
    }

    /**
     * 15位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    public static boolean is15Idcard(String idcard) {
        return idcard == null || "".equals(idcard) ? false : Pattern.matches(
                "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
                idcard);
    }

    /**
     * 18位身份证号码的基本数字和位数验校
     *
     * @param idcard
     * @return
     */
    public static boolean is18Idcard(String idcard) {
        return Pattern
                .matches(
                        "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
                        idcard);
    }


    public static String getScanCode(String url){


        if(TextUtil.isEmpty(url)){
            return null;
        }

        if(!(url.contains("dibaibike.com") || url.contains("angledog.net"))){
            return null;
        }

        String[] codes = url.split("/");

        if(codes.length == 0){
            return null;
        }
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(codes[codes.length - 1]);
        return m.replaceAll("").trim();
    }
}

