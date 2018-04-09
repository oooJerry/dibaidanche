package com.wuwutongkeji.dibaidanche.common.net;


import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.manager.LocationManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;
import com.wuwutongkeji.dibaidanche.common.rx.RxUtils;
import com.wuwutongkeji.dibaidanche.common.util.HttpUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.ConsumeEntity;
import com.wuwutongkeji.dibaidanche.entity.CreditEntity;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;
import com.wuwutongkeji.dibaidanche.entity.LockByGisEntity;
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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * Created by Mr.Bai on 2017/9/11.
 */
public class NetDataManager {

    private static NetDataManager dataManager;
    private static NetWorkManager mNetWorkManager;
    private static NetService mNetService;

    public synchronized static NetDataManager getInstance() {
        if (dataManager == null) {
            dataManager = new NetDataManager();
        }
        return dataManager;
    }
    private NetDataManager(){
        mNetWorkManager = NetWorkManager.getInstance();
        mNetService = mNetWorkManager.getNetService();
    }

    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    public Observable<String> user_fetchIdentifyCode(String mobile){
        return RxUtils.converterMode(mNetService.user_fetchIdentifyCode(mobile));
    }

    /****
     * 登录
     * @param map
     * @return
     */
    public Observable<LoginEntity> user_login(Map<String,Object> map){
        return RxUtils.converterMode(mNetService.user_login(map));
    }

    /****
     * 实名认证
     * @param idCard
     * @param name
     * @return
     */
    public Observable<NetModel<LoginEntity>> user_auth(String idCard, String name){
        return RxUtils.converterNillMode(mNetService.user_auth(idCard,name));
    }

    /***
     * 用户申诉
     * @param idCard
     * @param certificateFront
     * @param certificateBack
     * @return
     */
    public Observable<LoginEntity> user_appeal(String idCard,String name,String certificateFront,String certificateBack){
        return RxUtils.converterMode(mNetService.user_appeal(idCard,name,certificateFront,certificateBack));
    }

    /***
     * 更新deviceToken
     * @return
     */
    public Observable<Void> user_uploadDeviceToken(String token){
        return RxUtils.converterMode(mNetService.user_uploadDeviceToken(token));
    }


    /***
     * 获取用户信息、状态
     * @return
     */
    public Observable<NetModel<UserInfoEntity>> user_info(){
        return RxUtils.converterAllMode(mNetService.user_info());
    }

    /***
     * 获取用户信息、状态
     * @return
     */
    public Observable<UserInfoEntity> user_info_filter(){
        return RxUtils.converterMode(mNetService.user_info());
    }

    /***
     * 更新用户信息
     * @param nickname
     * @param photoUrl
     * @return
     */
    public Observable<LoginEntity> user_update(String nickname,String photoUrl){
        Map<String,String> map = new HashMap<>();
        if(!TextUtil.isEmpty(nickname)){
            map.put("nickname",nickname);
        }
        if(!TextUtil.isEmpty(photoUrl)){
            map.put("photoUrl",photoUrl);
        }
        return RxUtils.converterMode(mNetService.user_update(map));
    }




    /***
     * 查询车辆
     * @param lon
     * @param lat
     * @param radius
     * @param bicycleUsingStatus
     * @return
     */
    public Observable<NetModel<List<LockByGisEntity>>> bicycle_queryBicycleByGis(String lon, String lat, String radius, String bicycleUsingStatus){
        Map<String,String> map = new HashMap<>();
        map.put("lon",lon);
        map.put("lat",lat);
        map.put("radius",radius);
        map.put("bicycleUsingStatus", bicycleUsingStatus);

        return RxUtils.converterAllMode(mNetService.bicycle_queryBicycleByGis(map));
    }


    /**
     * 开锁
     * @param userId
     * @param bicycleNum
     * @return
     */
    public Observable<LockEntity> bicycle_openLock(String userId , String bicycleNum){
        Map<String,String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("bicycleNum",bicycleNum);
        map.put("lon", LocationManager.getLongitude());
        map.put("lat", LocationManager.getLatitude());

        return RxUtils.converterMode(mNetService.bicycle_openLock(map));
    }

    /**
     * 关锁
     * @return
     */
    public Observable<Void> bicycle_closeLock(String userId,String lockNum,
                                              String bicycleNum,String consumeId){
        Map<String,String> map = new HashMap<>();
        map.put("userId", userId);
        if(!TextUtil.isEmpty(lockNum)){
            map.put("lockNum",lockNum);
        }
        map.put("bicycleNum",bicycleNum);
        map.put("lon", LocationManager.getLongitude());
        map.put("lat", LocationManager.getLatitude());
        map.put("consumeId", consumeId);

        return RxUtils.converterMode(mNetService.bicycle_closeLock(map));
    }

    /***
     * 反馈选项列表
     * @return
     */
    public Observable<List<OptionTypeEntity>> feedBack_types(){
        return RxUtils.converterMode(mNetService.feedBack_types());
    }

    /***
     * 添加反馈
     * @param body
     * @return
     */
    public Observable<Void> feedBack_add(MultipartBody body){
        return RxUtils.converterMode(mNetService.feedBack_add(body));
    }

    /***
     * 报修选项
     * @return
     */
    public Observable<List<OptionTypeEntity>> repairs_types(){
        return RxUtils.converterMode(mNetService.repairs_types());
    }

    /***
     * 报修选项
     * @return
     */
    public Observable<Void> repairs_add( MultipartBody body){
        return RxUtils.converterMode(mNetService.repairs_add(body));
    }

    /**
     * 查询用户行程信息
     * @param pageNum
     * @return
     */
    public Observable<List<LockEntity>> consume_queryConsumeList(int pageNum){
        Map<String,String> map = new HashMap<>();
        map.put("userId",SharedPreferencesUtil.getUser().getId());
        map.put("pageSize",String.valueOf(AppConfig.PAGE_SIZE));
        map.put("pageNum",String.valueOf(pageNum));
        return RxUtils.converterMode(mNetService.consume_queryConsumeList(map));
    }


    /****
     * 查询用户详细行程
     * @param consumeId
     * @return
     */
    public Observable<ConsumeEntity> consume_queryConsumeState(String consumeId){
        return RxUtils.converterMode(mNetService.consume_queryConsumeState(consumeId));
    }
    /***
     * 充值明细
     * @param payId
     * @return
     */
    public Observable<WalletRecordEntity> pay_record(String payId){
        return RxUtils.converterAllMode(mNetService.pay_record(payId));
    }


    /****
     * 获取押金，最小用车余额，充值列表接口
     * @return
     */
    public Observable<DepositEntity> pay_depositAmount(){
        return RxUtils.converterMode(mNetService.pay_depositAmount());
    }


    /***
     * 获取充值押金支付凭证
     * @param payChannel
     * @return
     */
    public Observable<String> pay_depositChannel(String payChannel){
        return RxUtils.converterMode(mNetService.pay_depositChannel(payChannel));
    }

    /***
     * 获取充值年卡支付凭证
     * @param payChannel
     * @return
     */
    public Observable<String> pay_yearcard(String payChannel){
        return RxUtils.converterMode(mNetService.pay_yearcard(payChannel));
    }

    /**
     * 查询是否支付押金
     * @return
     */
    public Observable<QueryDepositEntity> pay_queryDeposit(){
        return RxUtils.converterMode(mNetService.pay_queryDeposit());
    }


    public Observable<String> pay_balance(String payChannel,long amount){
        return RxUtils.converterMode(mNetService.pay_balance(payChannel,String.valueOf(amount)));
    }
    /**
     * 退押金理由
     * @return
     */
    public Observable<List<String>> pay_refundReason(){
        return RxUtils.converterAllMode(mNetService.pay_refundReason());
    }


    /***
     * 退押金
     * @return
     */
    public Observable<String> pay_refundDeposit(String reason){
        return RxUtils.converterMode(mNetService.pay_refundDeposit(reason));
    }

    /***
     * 退押金状态
     */
    public Observable<NetModel<String>> pay_queryRefund(){
        return RxUtils.converterAllMode(mNetService.pay_queryRefund());
    }


    /***
     * 优惠券列表
     * @param couponId
     * @return
     */
    public Observable<WalletCouponEntity> coupon_fetch(String couponId){
        return RxUtils.converterAllMode(mNetService.coupon_fetch(couponId));
    }

    /**
     * 查询积分
     * @param creditScoreId
     * @return
     */
    public Observable<CreditEntity> credit_fetch(String creditScoreId){
        return RxUtils.converterAllMode(mNetService.credit_fetch(creditScoreId));
    }

    /***
     * 卡列表
     */
    public Observable<NetModel<List<FreeCardEntity>>> freeCard_list_all(String cardId){
        return RxUtils.converterAllMode(mNetService.freeCard_list(cardId));
    }
    /***
     * 卡列表
     */
    public Observable<NetModel<List<FreeCardEntity>>> freeCard_list(String cardId){
        return RxUtils.converterNillMode(mNetService.freeCard_list(cardId));
    }

    /***
     * 获取分享参数
     * @return
     */
    public Observable<ShareEntity> share_param(){
        return RxUtils.converterMode(mNetService.share_param());
    }

    /***
     * 上传文件
     * @param filePath
     * @return
     */
    public Observable<UploadFileEntity> uploadFile(String filePath, AppConfig.FileSource fileSource){
        File file = new File(filePath);
        String fileName = System.currentTimeMillis() + "_" + file.getName();
        MultipartBody.Builder mFormBuilder = new MultipartBody.Builder();
        mFormBuilder.addFormDataPart("name",fileName);
        mFormBuilder.addFormDataPart("file", fileName, HttpUtil.getFormRequest(file));
        mFormBuilder.addFormDataPart("source",fileSource.getSource());
        return RxUtils.converterMode(mNetService.uploadFile(mFormBuilder.build()));
    }

    /***
     * 获取最新版本
     * @return
     */
    public Observable<UploadEntity> version_last(){
        return RxUtils.converterMode(mNetService.version_last());
    }

}
