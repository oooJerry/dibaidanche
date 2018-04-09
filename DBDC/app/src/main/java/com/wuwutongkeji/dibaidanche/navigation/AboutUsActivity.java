package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 17/9/26.
 */

public class AboutUsActivity extends BaseToolbarActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.btn_protocol)
    LinearLayout btnProtocol;
    @BindView(R.id.btn_deposit)
    LinearLayout btnDeposit;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.btn_cell)
    LinearLayout btnCell;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_aboutus;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("关于我们");

        tvVersion.setText("v" + DeviceUtil.getVersionName());

        btnProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE,"用户协议");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.USER_PROTOCOL);
                startActivity(intent);
            }
        });
        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE,"押金说明");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.DEPOSIT_PROTOCOL);
                startActivity(intent);
            }
        });

        btnCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getTelActivity(getString(R.string.phone_customer)));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

}
