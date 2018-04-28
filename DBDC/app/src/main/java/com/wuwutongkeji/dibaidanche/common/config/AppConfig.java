package com.wuwutongkeji.dibaidanche.common.config;


import android.content.Context;
import android.content.Intent;

import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

import java.io.Serializable;

/**
 * Created by Mr.Bai on 2017/9/11.
 */

public class AppConfig {

    //缓存文件名字
    public static final String CACHENAME = "CACHE.DAT";
    //缓存文件夹名字
    public static String CACHEFILE = "/com/wuwutongkeji/dibaidanche";

    public static String DEVICE_TOKEN = "DEVICE_TOKEN";

    public static String CACHEFILE_PIC = CACHEFILE + "/pic";

    public static final int NET_ERROR_CODE = -999;

    public static final String NET_ERROR = "网络错误，请重试";

    public static final String NO_LOGIN = "NO_LOGIN";

    public static final int PAGE_SIZE = 10;


    // 客服电话
    public static final String PHONE_CUSTOMER = "4006192977";

    // 查询附近%d 米的车辆
    public static final int RADIUS_QUERYBICYCLEBYGIS = 10000;

    public static final String SHOWCARD_TIME = "SHOWCARD_TIME"; // 显示 卡的时间

    public class USERKEYS{

        public static final String AREA = "user_area";
        public static final String AUTHID = "user_authId";
        public static final String AUTHTIME = "user_authTime";
        public static final String BALANCE = "user_balance";
        public static final String BIRTH = "user_birth";
        public static final String CITY = "user_city";
        public static final String CREATETIME = "user_createTime";
        public static final String CREDIT = "user_credit";
        public static final String DISCOUNTCOUPON = "user_discountCoupon";
        public static final String ENABLE = "user_enable";
        public static final String GENDER = "user_gender";
        public static final String ID = "user_id";
        public static final String IDNUMBER = "user_idNumber";
        public static final String IDENTIFYCODE = "user_identifyCode";
        public static final String INVITATIONCODE = "user_invitationCode";
        public static final String LOGINTOKEN = "user_loginToken";
        public static final String MOBILE = "user_mobile";
        public static final String NAME = "user_name";
        public static final String NICKNAME = "user_nickname";
        public static final String PHOTOURL = "user_photoUrl";
        public static final String PAYDEPOSIT = "user_payDeposit";
        public static final String PAYBALANCE = "user_payBalance";
        public static final String PROVINCE = "user_province";
        public static final String REGISTERTIME = "user_registerTime";
        public static final String UPDATETIME = "user_updateTime";
        public static final String USECOUNT = "user_useCount";
    }

    public enum BikeUsingStatus {

        BICYCLE_FREE("BICYCLE_FREE"), // 空闲

        BICYCLE_OPENING("BICYCLE_OPENING"), // 开锁中

        BICYCLE_USING("BICYCLE_USING"), // 使用中

        BICYCLE_OPEN_ERROR("BICYCLE_FREE");// 开锁失败

        private String bicycleUsingStatus;

        BikeUsingStatus(String bicycleUsingStatus){
            this.bicycleUsingStatus = bicycleUsingStatus;
        }

        public String getBicycleUsingStatus() {
            return bicycleUsingStatus;
        }

    }

    public enum FileSource {

        ICON("ICON"), // 头像

        IDCARD("IDCARD"), // 身份证

        FEEDBACK("FEEDBACK"), // 反馈

        REPAIR("REPAIR");// 报修

        private String source;

        FileSource(String source){
            this.source = source;
        }

        public String getSource() {
            return source;
        }

    }

    public enum UserState implements Serializable{

        NO_LOGIN, // 未登录

        NO_PAY, // 未支付押金

        NO_AUTH; // 未认证
    }

    public enum LockType{

        LOCK_OPENED_SUCCESS(1, "已开锁"),
        LOCK_OPENING(2, "开锁中"),
        LOCK_OPENED_FAILED(3, "开锁失败"),
        TRAVEL_FINISHED(4, "行程结束");

        private int code;
        private String msg;

        LockType(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static LockType valueOfCode(int code){
            if(code == 1){
                return LOCK_OPENED_SUCCESS;
            }
            if(code == 2 || code == 0){
                return LOCK_OPENING;
            }
            if(code == 3){
                return LOCK_OPENED_FAILED;
            }
            if(code == 4){
                return TRAVEL_FINISHED;
            }
            return null;
        }
    }

    public static Intent user2Intent(LoginEntity entity, Context mContext){
        if(null == entity || TextUtil.isEmpty(entity.getLoginToken())){
            return AppIntent.getLoginActivity(mContext);
        }
//        if(!entity.isPayDeposit()){
//            return AppIntent.getDepositActivity(mContext);
//        }
        if(!entity.isAuthId()){
            return AppIntent.getApproveActivity(mContext);
        }
        return null;
    }
}
