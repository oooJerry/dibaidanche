package com.wuwutongkeji.dibaidanche.common.config;

/**
 * Created by Mr.Bai on 2017/9/11.
 */

public class AppInterface {


    // address
    public static final String ADDRESS = "http://mobileapi.dibaibike.com/dibai/";
    // 静态网页地址
    public static final String DEFAULT_ADDRESS = "http://mobileapi.dibaibike.com:9999/dibai/";


//    // address
//    public static final String ADDRESS = "http://test.dibaibike.com/dibai/";
//    // 静态网页地址
//    public static final String DEFAULT_ADDRESS = "http://test.dibaibike.com:9999/dibai/";

    /***
     * 发送验证码
      */
    public static final String USER_FETCHIDENTIFYCODE = "user/fetchIdentifyCode";


    /***
     * 登录
     */
    public static final String USER_LOGIN = "user/login";

    /***
     * 实名认证
     */
    public static final String USER_AUTH = "user/auth";


    /****
     * 身份申诉
     */
    public static final String USER_APPEAL = "user/appeal";


    /***
     * 更新deviceToken
     */
    public static final String USER_UPDATE_DVICETOKEN = "user/deviceToken";


    /***
     * 获取用户信息、状态
     */
    public static final String USER_INFO = "user/info";

    /***
     * 更新用户
     */
    public static final String USER_UPDATE = "user/update";


    /****
     * 查询车辆
     */
    public static final String BICYCLE_QUERYBICYCLEBYGIS = "bicycle/queryBicycleByGis";


    /****
     * 开锁
     */
    public static final String BICYCLE_OPENLOCK = "bicycle/openLock";

    /****
     * 关锁
     */
    public static final String BICYCLE_CLOSELOCK = "bicycle/closeLock";


    /****
     * 反馈选项列表
     */
    public static final String FEEDBACK_TYPES = "feedBack/getFeedBackTypes";

    /****
     * 反馈提交
     */
    public static final String FEEDBACK_ADD = "feedBack/addFeedBack";

    /***
     * 报修选项
     */
    public static final String REPAIRS_TYPES = "repairs/getRepairsTypes";


    /***
     * 报修提交
     */
    public static final String REPAIRS_ADD = "repairs/addRepairs";

    /****
     * 查询行程信息
     */
    public static final String CONSUME_QUERYCONSUMELIST = "consume/queryConsumeList";

    /****
     * 查询详细行程信息
     */
    public static final String CONSUME_QUERYCONSUMESTATE = "consume/queryConsumeState";


    /***
     * 获取押金，最小用车余额，充值列表接口
     */
    public static final String PAY_DEPOSITAMOUNT = "pay/amount/limit";

    /***
     * 获取支付凭证
     */
    public static final String PAY_DEPOSITCHANNEL = "pay/deposit";
    /***
     * 获取充值年卡支付凭证
     */
    public static final String PAY_YEARCARD = "pay/yearcard";

    /***
     * 充值明细
     */
    public static final String PAY_RECORD = "pay/record";

    /**
     * 查询是否支付押金
     */
    public static final String PAY_QUERYDEPOSIT = "pay/query/deposit";

    /**
     * 充值押金
     */
    public static final String PAY_BALANCE = "pay/balance";


    /**
     * 退押金理由
     */
    public static final String PAY_REFUNDREASON = "pay/refund/reason";

    /***
     * 退押金
     */
    public static final String PAY_REFUNDDEPOSIT = "pay/refund/deposit";

    /***
     * 退押金状态
     */
    public static final String PAY_QUERYREFUND = "pay/query/refund";

    /***
     * 优惠券列表
     */
    public static final String COUPON_FETCH = "coupon/fetch";

    /**
     * 我的明细列表
     */
    public static final String CREDIT_FETCH = "credit/fetch";

    /**
     * 卡的列表
     */
    public static final String FREECARD_LIST = "freeCard/list";
    /**
     * 获取分享
     */
    public static final String SHARE_PARAM = "share/param";

    /***
     * 邀请好友
     */
    public static final String SHARE_CODE = "share/code";
    /***
     * 上传文件
     */
    public static final String UPLOADFILE = "upload/file";

    /***
     * 检查新版本
     */
    public static final String VERSION_LAST = "version/last";



    // 用户指南-如何报修
    public static final String HOWTO_REPAIRS = DEFAULT_ADDRESS + "howto-repairs.html";
    // 如何关锁与复位密码
    public static final String HOWTO_CLOSE = DEFAULT_ADDRESS + "howto-close.html";
    // 用户指南-使用的拜单车如何计费
    public static final String HOWTO_FINISH = DEFAULT_ADDRESS + "howto-finish.html";
    // 用户指南-实名认证
    public static final String HOWTO_AUTH = DEFAULT_ADDRESS + "howto-auth.html";
    //用户指南-如何与客服人员联系
    public static final String HOWTO_CONNECT = DEFAULT_ADDRESS + "howto-connect.html";
    //用户指南-邀请好友后，为何没有相应奖励
    public static final String HOWTO_NOREWARD = DEFAULT_ADDRESS + "howto-noreward.html";
    //用户指南-押金是多少
    public static final String HOWTO_HOWMUCH = DEFAULT_ADDRESS + "howto-howmuch.html";
    //用户指南-如何取车与停车
    public static final String HOWTO_STOP = DEFAULT_ADDRESS + "howto-stop.html";
    //用户指南-如何开锁
    public static final String HOWTO_OPEN = DEFAULT_ADDRESS + "howto-open.html";
    //用户协议
    public static final String USER_PROTOCOL = DEFAULT_ADDRESS + "user-protocol.html";
    // 信用分规则
    public static final String CREDIT_RULE = DEFAULT_ADDRESS + "credit-rule.html";
    // 充值协议
    public static final String PAY_PROTOCOL = DEFAULT_ADDRESS + "pay-protocol.html";
    // 押金说明
    public static final String DEPOSIT_PROTOCOL = DEFAULT_ADDRESS + "deposit-protocol.html";
    // 保险内容
    public static final String NSURANCE_PROTOCOL = DEFAULT_ADDRESS + "nsurance-protocol.html";
    // 邀请好友
    public static final String INVITE_USER = DEFAULT_ADDRESS + "invite-user.html";
    // 余额说明
    public static final String BALANCE_PROTOCOL = DEFAULT_ADDRESS + "balance-protocol.html";
}
