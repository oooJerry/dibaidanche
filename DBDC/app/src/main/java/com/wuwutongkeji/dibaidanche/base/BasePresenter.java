package com.wuwutongkeji.dibaidanche.base;

import android.app.Dialog;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.net.NetDataManager;
import com.wuwutongkeji.dibaidanche.common.net.NetWorkManager;


/**
 * Created by Mr.Bai on 2017/9/11.
 */
public abstract class BasePresenter<T extends BaseDependView> {

    protected NetDataManager mNetDataManager;

    protected Dialog mDialog;

    protected T mDependView;

    private BasePresenter BasePresenter(Builder builder){
        mDependView = builder.mDependView;
        mDialog = builder.mDialog;
        mNetDataManager = builder.mNetDataManager;
        onAttach();
        return this;
    }

    protected void onAttach(){}

    public Builder newBuilder(){
        return new Builder();
    }

    public void showErrorMsg(){
        mDependView.showMsg(AppConfig.NET_ERROR);
    }

    public final class Builder{

        private NetDataManager mNetDataManager;

        private Dialog mDialog;

        private T mDependView;

        public Builder proceed(T view){
            mDependView = view;
            mNetDataManager = NetWorkManager.getInstance().getNetDataManager();
            return this;
        }

        public Builder dialog(Dialog mDialog){
            this.mDialog = mDialog;
            return this;
        }

        public <P extends BasePresenter> P build(){
//            Class classz = mDependView.getPresenter().getClass();
//            try {
//                Constructor c = classz.getConstructor(Builder.class);
//                return (P) c.newInstance(Builder.this);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return (P) BasePresenter.this.BasePresenter(this);
        }
    }
}
