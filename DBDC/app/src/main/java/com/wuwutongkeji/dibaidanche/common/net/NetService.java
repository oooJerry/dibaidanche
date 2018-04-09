package com.wuwutongkeji.dibaidanche.common.net;

import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;
import com.wuwutongkeji.dibaidanche.entity.ConsumeEntity;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;
import com.wuwutongkeji.dibaidanche.entity.LockByGisEntity;
import com.wuwutongkeji.dibaidanche.entity.CreditEntity;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.OptionTypeEntity;
import com.wuwutongkeji.dibaidanche.entity.QueryDepositEntity;
import com.wuwutongkeji.dibaidanche.entity.ShareEntity;
import com.wuwutongkeji.dibaidanche.entity.UploadEntity;
import com.wuwutongkeji.dibaidanche.entity.UploadFileEntity;
import com.wuwutongkeji.dibaidanche.entity.UserInfoEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletCouponEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletRecordEntity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Mr.Bai on 2017/9/11.
 */
public interface NetService {


    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.USER_FETCHIDENTIFYCODE)
    Observable<NetModel<String>> user_fetchIdentifyCode(@Field("mobile") String mobile);


    /**
     * 登录
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.USER_LOGIN)
    Observable<NetModel<LoginEntity>> user_login(@FieldMap Map<String,Object> map);


    /***
     * 实名认证
     * @param idCard
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.USER_AUTH)
    Observable<NetModel<LoginEntity>> user_auth(@Field("idCard") String idCard, @Field("name") String name);


    /***
     * 实名认证-申诉
     * @param idCard
     * @param name
     * @param certificateFront
     * @param certificateBack
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.USER_APPEAL)
    Observable<NetModel<LoginEntity>> user_appeal(@Field("idCard") String idCard,
                                                  @Field("name") String name,
                                                  @Field("certificateFront") String certificateFront,
                                                  @Field("certificateBack") String certificateBack);

    /**
     * 更新deviceToken
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.USER_UPDATE_DVICETOKEN)
    Observable<NetModel<Void>> user_uploadDeviceToken(@Field("deviceToken") String deviceToken);



    /**
     * 获取用户信息、状态
     * @return
     */
    @POST(AppInterface.USER_INFO)
    Observable<NetModel<UserInfoEntity>> user_info();



    @FormUrlEncoded
    @POST(AppInterface.USER_UPDATE)
    Observable<NetModel<LoginEntity>> user_update(@FieldMap Map<String,String> map);
    /*****
     * 查询车辆
     * @param map
     * @return
     */
    @GET(AppInterface.BICYCLE_QUERYBICYCLEBYGIS)
    Observable<NetModel<List<LockByGisEntity>>> bicycle_queryBicycleByGis(@QueryMap Map<String,String> map);

    /*****
     * 开锁
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.BICYCLE_OPENLOCK)
    Observable<NetModel<LockEntity>> bicycle_openLock(@FieldMap Map<String,String> map);

    /*****
     * 关锁
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.BICYCLE_CLOSELOCK)
    Observable<NetModel<Void>> bicycle_closeLock(@FieldMap Map<String,String> map);

    /****
     * 反馈选项列表
     * @return
     */
    @GET(AppInterface.FEEDBACK_TYPES)
    Observable<NetModel<List<OptionTypeEntity>>> feedBack_types();

    /****
     * 反馈提交
     * @return
     */
    @POST(AppInterface.FEEDBACK_ADD)
    Observable<NetModel<Void>> feedBack_add(@Body MultipartBody body);

    /**
     * 报修选项
     * @return
     */
    @GET(AppInterface.REPAIRS_TYPES)
    Observable<NetModel<List<OptionTypeEntity>>> repairs_types();


    @POST(AppInterface.REPAIRS_ADD)
    Observable<NetModel<Void>> repairs_add(@Body MultipartBody body);


    /***
     * 查询行程信息
     */
    @GET(AppInterface.CONSUME_QUERYCONSUMELIST)
    Observable<NetModel<List<LockEntity>>> consume_queryConsumeList(@QueryMap Map<String,String> map);

    /***
     * 查询行程详细信息
     * @param consumeId
     * @return
     */
    @GET(AppInterface.CONSUME_QUERYCONSUMESTATE)
    Observable<NetModel<ConsumeEntity>> consume_queryConsumeState(@Query("consumeId") String consumeId);

    /***
     * 充值明细
     * @param payId
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.PAY_RECORD)
    Observable<WalletRecordEntity> pay_record(@Field("payId") String payId);

    /***
     * 获取押金，最小用车余额，充值列表接口
     * @return
     */
    @POST(AppInterface.PAY_DEPOSITAMOUNT)
    Observable<NetModel<DepositEntity>> pay_depositAmount();

    /**
     * 获取支付凭证
     * @param payChannel
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.PAY_DEPOSITCHANNEL)
    Observable<NetModel<String>> pay_depositChannel(@Field("payChannel") String payChannel);

    @FormUrlEncoded
    @POST(AppInterface.PAY_YEARCARD)
    Observable<NetModel<String>> pay_yearcard(@Field("payChannel") String payChannel);

    /**
     * 查询是否支付押金
     * @return
     */
    @POST(AppInterface.PAY_QUERYDEPOSIT)
    Observable<NetModel<QueryDepositEntity>> pay_queryDeposit();


    @FormUrlEncoded
    @POST(AppInterface.PAY_BALANCE)
    Observable<NetModel<String>> pay_balance(@Field("payChannel") String payChannel,@Field("amount") String amount);

    /***
     * 退押金理由
     * @return
     */
    @POST(AppInterface.PAY_REFUNDREASON)
    Observable<List<String>>pay_refundReason();


    /**
     * 退押金
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.PAY_REFUNDDEPOSIT)
    Observable<NetModel<String>> pay_refundDeposit(@Field("reason") String reason);


    /***
     * 退押金状态
     * @return
     */
    @POST(AppInterface.PAY_QUERYREFUND)
    Observable<NetModel<String>> pay_queryRefund();

    /***
     * 查询优惠券列表
     * @param couponId
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.COUPON_FETCH)
    Observable<WalletCouponEntity> coupon_fetch(@Field("couponId") String couponId);

    /***
     * 我的信用明细
     * @param creditScoreId
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.CREDIT_FETCH)
    Observable<CreditEntity> credit_fetch(@Field("creditScoreId") String creditScoreId);

    /***
     * 卡的列表
     * @return
     */
    @FormUrlEncoded
    @POST(AppInterface.FREECARD_LIST)
    Observable<NetModel<List<FreeCardEntity>>> freeCard_list(@Field("cardId") String cardId);

    /****
     * 分享参数
     * @return
     */

    @POST(AppInterface.SHARE_PARAM)
    Observable<NetModel<ShareEntity>> share_param();
    /**
     * 上传文件
     * @param file
     * @return
     */
    @POST(AppInterface.UPLOADFILE)
    Observable<NetModel<UploadFileEntity>> uploadFile(@Body MultipartBody file);

    /***
     * 检查新版本
     * @return
     */
    @POST(AppInterface.VERSION_LAST)
    Observable<NetModel<UploadEntity>> version_last();
}
