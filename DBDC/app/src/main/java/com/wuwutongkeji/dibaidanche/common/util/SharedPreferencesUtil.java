package com.wuwutongkeji.dibaidanche.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wuwutongkeji.dibaidanche.LaunchApp;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Mr.Bai on 2017/9/12.
 */
public class SharedPreferencesUtil {

    private static final String INFO_NAME = AppConfig.CACHENAME;

    private static SharedPreferences sp = LaunchApp.Instance.getSharedPreferences(INFO_NAME,
            Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sp.edit();

    public static void write(Map<String, String> params) {
        Iterator<String> iterator = params.keySet().iterator();
        for (; iterator.hasNext();) {
            String key = iterator.next();
            editor.putString(key, params.get(key));
        }
        editor.commit();
    }

    /**
     * 写文件
     *
     * @param key
     * @param value
     */
    public static void write(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 写文件
     *
     * @param key
     * @param value
     */
    public static void writeBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void writeInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void writeLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public static void writeDouble(String key, double value) {
        editor.putFloat(key, (float) value);
        editor.commit();
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */

    public static String read(String key) {
        return read(key, "");
    }
    /**
     * 读取字符串
     *
     * @param key
     * @return
     */

    public static String read(String key, String def) {
        return sp.getString(key, def);
    }

    /**
     * 读取布尔型数据，默认为false
     *
     * @param key
     * @return
     */
    public static boolean readBoolean(String key) {
        return readBoolean(key, false);
    }

    /**
     * 读取布尔型数据
     *
     * @param key
     * @param def
     * @return
     */
    public static boolean readBoolean(String key, boolean def) {
        return sp.getBoolean(key, def);
    }

    /**
     * 读取整形数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int readInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static double readDouble(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    /**
     * 读取长整形数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long readLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public static void clear(String key){
        editor.remove(key);
        editor.commit();
    }

    public static void clearAll(){
        editor.clear();
        editor.commit();
    }


    public static void saveUser(LoginEntity entity){

        write(AppConfig.USERKEYS.AREA,entity.getArea());
        writeBoolean(AppConfig.USERKEYS.AUTHID,entity.isAuthId());
        if(null != entity.getAuthTime()){
            writeLong(AppConfig.USERKEYS.AUTHTIME,entity.getAuthTime().getTime());
        }
        writeLong(AppConfig.USERKEYS.BALANCE,entity.getBalance());
        write(AppConfig.USERKEYS.BIRTH,entity.getBirth());
        write(AppConfig.USERKEYS.CITY,entity.getCity());
        write(AppConfig.USERKEYS.CREATETIME,entity.getCreateTime());
        write(AppConfig.USERKEYS.CREDIT,entity.getCredit());
        write(AppConfig.USERKEYS.DISCOUNTCOUPON,entity.getDiscountCoupon());
        write(AppConfig.USERKEYS.ENABLE,entity.getEnable());
        write(AppConfig.USERKEYS.GENDER,entity.getGender());
        write(AppConfig.USERKEYS.ID,entity.getId());
        write(AppConfig.USERKEYS.IDNUMBER,entity.getIdNumber());
        write(AppConfig.USERKEYS.IDENTIFYCODE,entity.getIdentifyCode());
        write(AppConfig.USERKEYS.INVITATIONCODE,entity.getInvitationCode());
        write(AppConfig.USERKEYS.LOGINTOKEN,entity.getLoginToken());
        write(AppConfig.USERKEYS.MOBILE,entity.getMobile());
        write(AppConfig.USERKEYS.NAME,entity.getName());
        write(AppConfig.USERKEYS.NICKNAME,entity.getNickname());
        write(AppConfig.USERKEYS.PHOTOURL,entity.getPhotoUrl());
        writeBoolean(AppConfig.USERKEYS.PAYDEPOSIT,entity.isPayDeposit());
        writeBoolean(AppConfig.USERKEYS.PAYBALANCE,entity.isPayBalance());
        write(AppConfig.USERKEYS.PROVINCE,entity.getProvince());
        if(null != entity.getRegisterTime()){
            writeLong(AppConfig.USERKEYS.REGISTERTIME,entity.getRegisterTime().getTime());
        }
        if(null != entity.getUpdateTime()){
            writeLong(AppConfig.USERKEYS.UPDATETIME,entity.getUpdateTime().getTime());
        }
        writeInt(AppConfig.USERKEYS.USECOUNT,entity.getUseCount());
    }

    public static LoginEntity getUser(){

        LoginEntity entity = new LoginEntity();

        entity.setArea(read(AppConfig.USERKEYS.AREA));
        entity.setAuthId(readBoolean(AppConfig.USERKEYS.AUTHID));
        entity.setAuthTime(new Date(readLong(AppConfig.USERKEYS.AUTHTIME,0)));
        entity.setBalance(readLong(AppConfig.USERKEYS.BALANCE,0));
        entity.setBirth(read(AppConfig.USERKEYS.BIRTH));
        entity.setCity(read(AppConfig.USERKEYS.CITY));
        entity.setCreateTime(read(AppConfig.USERKEYS.CREATETIME));
        entity.setCredit(read(AppConfig.USERKEYS.CREDIT));
        entity.setDiscountCoupon(read(AppConfig.USERKEYS.DISCOUNTCOUPON));
        entity.setEnable(read(AppConfig.USERKEYS.ENABLE));
        entity.setGender(read(AppConfig.USERKEYS.GENDER));
        entity.setId(read(AppConfig.USERKEYS.ID));
        entity.setIdNumber(read(AppConfig.USERKEYS.IDNUMBER));
        entity.setIdentifyCode(read(AppConfig.USERKEYS.IDENTIFYCODE));
        entity.setInvitationCode(read(AppConfig.USERKEYS.INVITATIONCODE));
        entity.setLoginToken(read(AppConfig.USERKEYS.LOGINTOKEN));
        entity.setMobile(read(AppConfig.USERKEYS.MOBILE));
        entity.setName(read(AppConfig.USERKEYS.NAME));
        entity.setNickname(read(AppConfig.USERKEYS.NICKNAME));
        entity.setPhotoUrl(read(AppConfig.USERKEYS.PHOTOURL));
        entity.setPayDeposit(readBoolean(AppConfig.USERKEYS.PAYDEPOSIT));
        entity.setPayBalance(readBoolean(AppConfig.USERKEYS.PAYBALANCE));
        entity.setProvince(read(AppConfig.USERKEYS.PROVINCE));
        entity.setRegisterTime(new Date(readLong(AppConfig.USERKEYS.REGISTERTIME,0)));
        entity.setUpdateTime(new Date(readLong(AppConfig.USERKEYS.UPDATETIME,0)));
        entity.setUseCount(readInt(AppConfig.USERKEYS.USECOUNT,0));

        return entity;
    }
}
