package com.wuwutongkeji.dibaidanche.launch.contract.login;

import com.wuwutongkeji.dibaidanche.common.manager.LocationManager;
import com.wuwutongkeji.dibaidanche.common.manager.PushManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.rx.RxUtils;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;
import com.wuwutongkeji.dibaidanche.common.util.FieldUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.$LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Mr.Bai on 17/9/14.
 */

public class LoginPresenter extends LoginContract.LoginBasePresenter {

    int totalTimer;

    Subscription timerSubscription;

    String verifyCodeData = "123";

    @Override
    public boolean timerIsRun() {
        if(null == timerSubscription){
            return false;
        }
        return !timerSubscription.isUnsubscribed();
    }

    @Override
    public void sendVerifyCode(String mobile) {

        if(!TextUtil.isCheckPhone(mobile)){
            mDependView.showMsg("请输入正确的手机号");
            return;
        }


        mNetDataManager.user_fetchIdentifyCode(mobile)
                .subscribe(new DefaultNetSubscriber<String>(null) {
                    @Override
                    public void onCompleted(String s) {
                        verifyCodeData = s;
                        runTimer();
                    }
                });
    }

    @Override
    public void login(String mobile, String verifyCode, String inviteCode) {

        if(!TextUtil.isCheckPhone(mobile)){
            mDependView.showMsg("请输入正确的手机号");
            return;
        }

        if(TextUtil.isEmpty(verifyCodeData)){
            mDependView.showMsg("请获取验证码");
            return;
        }

        if(TextUtil.isEmpty(verifyCode)){
            mDependView.showMsg("请输入验证码");
        }

        if(!TextUtil.isEmpty(inviteCode)){

            if(!TextUtil.isCheckPhone(inviteCode)){
                mDependView.showMsg("请输入正确的邀请码");
                return;
            }
            if(mobile.equals(inviteCode)){
                mDependView.showMsg("手机号不能和邀请码一致");
                return;
            }
        }
        $LoginEntity $loginEntity = new $LoginEntity();
        $loginEntity.identifyCode = verifyCode;
        $loginEntity.identifyCodeId = verifyCodeData;
        $loginEntity.mobileNumber = mobile;
        $loginEntity.mobileBrand = DeviceUtil.getPhoneBrand();
        $loginEntity.mobileModel = DeviceUtil.getPhoneModel();
        $loginEntity.mobileSystem = "ANDROID";
        $loginEntity.mobileSystemVersion = DeviceUtil.getBuildVersion();
        $loginEntity.loginLocation = LocationManager.getLongitude() + "|" + LocationManager.getLatitude();
        $loginEntity.invitationCode = inviteCode;
        $loginEntity.deviceToken = PushManager.getInstance().getDeviceToken();
        $loginEntity.appVersion = DeviceUtil.getVersionName();

        mNetDataManager.user_login(FieldUtil.Model2Map($loginEntity))
                .subscribe(new DefaultNetSubscriber<LoginEntity>(mDialog) {
                    @Override
                    public void onCompleted(LoginEntity loginEntity) {
                        SharedPreferencesUtil.saveUser(loginEntity);
                        mDependView.onBusinessFinish(loginEntity);
                        onDestroyTimer();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void runTimer(){

        totalTimer = 60;

        Observable<Long> timer = Observable.interval(0,1, TimeUnit.SECONDS)
                .compose(RxUtils.<Long>applyIOToMainThreadSchedulers());

        timerSubscription = timer.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                totalTimer -= 1;

                boolean enable = totalTimer <= 0;
                mDependView.timerofCode(enable,String.valueOf(totalTimer));
                if(enable){
                    onDestroyTimer();
                }
            }
        });
    }

    @Override
    public void onDestroyTimer() {
        if(null != timerSubscription ){
            timerSubscription.unsubscribe();
        }
    }
}
