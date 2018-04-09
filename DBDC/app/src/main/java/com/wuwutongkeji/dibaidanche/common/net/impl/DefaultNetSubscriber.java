package com.wuwutongkeji.dibaidanche.common.net.impl;

import android.app.Dialog;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.entity.MessageEntity;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * Created by Mr.Bai on 2017/9/11.
 */
public abstract class DefaultNetSubscriber<T> extends Subscriber<T> {

    T data;

    protected Dialog dialog;


    public DefaultNetSubscriber(Dialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void onCompleted() {
        if(dialog != null){
            dialog.dismiss();
        }
        onCompleted(data);
    }

    public abstract void onCompleted(T t);

    @Override
    public void onError(Throwable e) {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        MessageEntity msgEntity = new MessageEntity();
        msgEntity.msg = e.getMessage();
        msgEntity.code = AppConfig.NET_ERROR_CODE;
        EventBus.getDefault().post(msgEntity);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        data = t;
    }

    /*


    public abstract static class Builder<T> {

        ShapeLoadingDialog shapeLoadingDialog;

        Context mContext;

        public Builder(Context mContext){
            this.mContext = mContext;
        }

        public Builder(){}

        public Builder ShapeLoadingDialog(ShapeLoadingDialog mShapeLoadingDialog){
            shapeLoadingDialog = mShapeLoadingDialog;
            return this;
        }

        public <T>DefaultSubscriber<T> create(){
            DefaultSubscriber defaultSubscriber = new DefaultSubscriber();
            defaultSubscriber.shapeLoadingDialog = shapeLoadingDialog;
            return defaultSubscriber;
        }
    }
*/

}
