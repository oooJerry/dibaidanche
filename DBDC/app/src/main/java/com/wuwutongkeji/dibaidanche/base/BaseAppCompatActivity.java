package com.wuwutongkeji.dibaidanche.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.manager.UploadManager;
import com.wuwutongkeji.dibaidanche.common.popup.LoadingDialog;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.MessageEntity;
import com.wuwutongkeji.dibaidanche.launch.LoginActivity;
import com.wuwutongkeji.dibaidanche.launch.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mr.Bai on 2017/9/7.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();

    protected Context mContext;

    protected View mDecoView;

    protected Dialog mLoadingDialog;

    private boolean isShowLoadingDialog;
    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecoView = View.inflate(this,this.getLayoutId(),null);
        this.setContentView(mDecoView);
        unbinder = ButterKnife.bind(this);

        mContext = this;

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                isShowLoadingDialog = true;
            }
        });

        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShowLoadingDialog = false;
            }
        });

        this.initViews(savedInstanceState);
        this.initToolbar(savedInstanceState);
        this.initData();
        UploadManager.checkUpdate(mContext);
    }

    public <T extends BasePresenter,V extends BaseDependView> T  newPresenter(T t ,V v){
        return (T) t.newBuilder()
                .proceed(v)
                .dialog(mLoadingDialog)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showError(MessageEntity entity){
        showMsg(entity.msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void noLogin(String code){
        if(AppConfig.NO_LOGIN.equals(code)){
            if(!(this instanceof MainActivity || this instanceof LoginActivity)){
                return;
            }
            SharedPreferencesUtil.clearAll();
            Intent intent = AppIntent.getMainActivity(this)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void showMsg(String msg){
        Toast toast = Toast.makeText(getApplicationContext(),
                msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isShowLoadingDialog){
            mLoadingDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isShowLoadingDialog){
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract void initData();

}