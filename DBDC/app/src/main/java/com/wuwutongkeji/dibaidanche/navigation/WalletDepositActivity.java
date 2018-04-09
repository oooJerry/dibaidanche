package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletDepositContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletDepositPresenter;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletDepositActivity extends BaseToolbarActivity implements WalletDepositContract.WalletDepositBaseView {

    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.btn_refund)
    Button btnRefund;

    WalletDepositContract.WalletDepositBasePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_wallet_deposit;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("我的押金");

        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onDepositRefund(WalletDepositActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new WalletDepositPresenter(),this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void onShowDeposit(String deposit) {
        tvDeposit.setText(deposit);
    }

    @Override
    public void onStartActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public WalletDepositContract.WalletDepositBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
