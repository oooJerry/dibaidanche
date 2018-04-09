package com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.UploadFileEntity;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class PersonalInfoPresenter extends PersonalInfoContract.PersonalInfoBasePresenter {

    @Override
    public void onLoadUserInfo() {
        LoginEntity loginEntity = SharedPreferencesUtil.getUser();
        mDependView.onLoadUserInfo(loginEntity);
    }

    @Override
    public void uploadFile(final String file) {

        final String[] imgUrl = new String[1];
        mNetDataManager.uploadFile(file, AppConfig.FileSource.ICON)
                .flatMap(new Func1<UploadFileEntity, Observable<String>>() {
                    @Override
                    public Observable<String> call(final UploadFileEntity uploadFileEntity) {
                        return Observable.create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                if(TextUtil.isEmpty(uploadFileEntity.getUrl())){
                                    subscriber.onError(new NullPointerException());
                                    return;
                                }
                                subscriber.onNext(uploadFileEntity.getUrl());
                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .flatMap(new Func1<String, Observable<LoginEntity>>() {
                    @Override
                    public Observable<LoginEntity> call(String s) {
                        imgUrl[0] = s;
                        return mNetDataManager.user_update(null,s);
                    }
                })
                .subscribe(new DefaultNetSubscriber<LoginEntity>(mDialog) {
                    @Override
                    public void onCompleted(LoginEntity entity) {
                        LoginEntity loginEntity = SharedPreferencesUtil.getUser();
                        loginEntity.setPhotoUrl(imgUrl[0]);
                        SharedPreferencesUtil.saveUser(loginEntity);
                        mDependView.onLoadUserInfoSucc(imgUrl[0]);
                        EventBus.getDefault().post(loginEntity);
                    }
                });

    }
}
