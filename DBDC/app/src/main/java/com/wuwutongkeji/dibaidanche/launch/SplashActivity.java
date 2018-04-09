package com.wuwutongkeji.dibaidanche.launch;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseAppCompatActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;

/**
 * Created by Mr.Bai on 2017/9/27.
 */

public class SplashActivity extends BaseAppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(AppIntent.getMainActivity(mContext));
                finish();
            }
        },3000);

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
    }
}
