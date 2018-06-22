package com.wuwutongkeji.dibaidanche.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.launch.contract.deposit.DepositContract;
import com.wuwutongkeji.dibaidanche.launch.contract.deposit.DepositPresenter;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/12.
 */

public class DepositActivity extends BaseToolbarActivity implements DepositContract.DepositBaseView{

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.aliPay_radio)
    RadioButton aliPayRadio;
    @BindView(R.id.btn_moreType)
    LinearLayout btnMoreType;
    @BindView(R.id.btn_alipay)
    LinearLayout btnAlipay;
    @BindView(R.id.wechat_radio)
    RadioButton wechatRadio;
    @BindView(R.id.btn_wechat)
    LinearLayout btnWechat;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    DepositContract.DepositBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_deposit;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("交纳押金");

        btnAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPayRadio.setChecked(true);
            }
        });

        btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatRadio.setChecked(true);
            }
        });

        btnMoreType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showMoreType(btnMoreType,btnWechat);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onPay(aliPayRadio.isChecked() ?
                        PayManager.PayChannel.ALIPAY : PayManager.PayChannel.WECHATPAY);
            }
        });
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new DepositPresenter(),this);

    }

    @Override
    public DepositContract.DepositBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        Intent intent = AppConfig.user2Intent((LoginEntity) serializable,mContext);
        if(null != intent){
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void showMinBalance(String balance) {
        tvMoney.setText(balance);
    }

    @Override
    public void onPay(PayManager.PayChannel payChannel, String orderInfo) {

        if(payChannel == PayManager.PayChannel.ALIPAY){
            PayManager.aliPay(orderInfo,this,mPresenter.onPayListener());
        }else if(payChannel == PayManager.PayChannel.WECHATPAY){
            PayManager.weChatPay(this,orderInfo,mPresenter.onPayListener());
        }
    }


}
