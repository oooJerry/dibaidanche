package com.wuwutongkeji.dibaidanche.bike.service;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.net.NetDataManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.rx.RxUtils;
import com.wuwutongkeji.dibaidanche.entity.ConsumeEntity;
import com.wuwutongkeji.dibaidanche.entity.RidingAptitudeOptionEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Mr.Bai on 17/10/7.
 */

public class RidingAptitudeService{

    List<Subscription> mSubscriptions = new ArrayList<>();


    public RidingAptitudeService(){
        EventBus.getDefault().register(this);
    }

    public static RidingAptitudeService init() {
        return new RidingAptitudeService();
    }


    // 轮询锁状态
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onPollingRidingAptitude(final RidingAptitudeOptionEntity entity){

        if(null == entity){
            return;
        }
        Subscription mSubscription = Observable.interval(0,10, TimeUnit.SECONDS)
                        .subscribe(new Subscriber<Long>() {

                            @Override
                            public void onStart() {
                                super.onStart();
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onNext(Long aLong) {

                                NetDataManager.getInstance().consume_queryConsumeState(entity.getConsumeId())
                                        .subscribe(new DefaultNetSubscriber<ConsumeEntity>(null) {
                                            @Override
                                            public void onCompleted(ConsumeEntity consumeEntity) {

                                                AppConfig.LockType cousumeType = AppConfig.LockType.valueOfCode(
                                                        consumeEntity.getCousumeStatus());

                                                if(entity.getLockType() == AppConfig.LockType.LOCK_OPENING
                                                        && cousumeType == AppConfig.LockType.LOCK_OPENED_SUCCESS){
                                                    EventBus.getDefault().post(cousumeType);
                                                }

                                                if(cousumeType == AppConfig.LockType.TRAVEL_FINISHED
                                                        || cousumeType == AppConfig.LockType.LOCK_OPENED_FAILED){
                                                    EventBus.getDefault().post(cousumeType);
                                                    for(Subscription subscription : mSubscriptions){
                                                        subscription.unsubscribe();
                                                        subscription = null;
                                                    }
                                                    mSubscriptions.clear();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                            }
                                        });

                            }
                        });

        if(!mSubscriptions.contains(mSubscription)){
            mSubscriptions.add(mSubscription);
        }
    }
}
