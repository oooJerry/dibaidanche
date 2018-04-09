package com.wuwutongkeji.dibaidanche.common.popup;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.widget.ProgressLoaingView;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/19.
 */

public class UnLockLoadingDialog extends BaseDialog {

    @BindView(R.id.loadingView)
    ProgressLoaingView loadingView;
    @BindView(R.id.tv_progress)
    TextView tvProgress;

    LockEntity mLockData;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_unlock_loading;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        loadingView.setOnUpdateListener(new ProgressLoaingView.UpdateListener() {
            @Override
            public void onProgress(int progress) {
                tvProgress.setText(progress + "%");
            }
        });
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        loadingView.onStop();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UnLockLoadingDialog.super.dismiss();
            }
        },1000);

    }

    @Override
    public void initDatas() {

    }

    public void setLockData(LockEntity data){
        mLockData = data;
    }

    public LockEntity getLockData(){
        return mLockData;
    }
}
