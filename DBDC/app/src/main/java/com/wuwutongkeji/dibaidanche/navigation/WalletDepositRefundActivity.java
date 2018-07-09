package com.wuwutongkeji.dibaidanche.navigation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.WalletDepositRefundAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletDepositRefundContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletDepositRefundPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletDepositRefundActivity extends BaseToolbarActivity
        implements WalletDepositRefundContract.WalletDepositRefundBaseView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_refund)
    Button btnRefund;
    @BindView(R.id.btn_finish)
    Button btnFinish;

    WalletDepositRefundAdapter mAdapter;

    WalletDepositRefundContract.WalletDepositRefundBasePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_wallet_deposit_refund;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("退押金");

        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(mAdapter = new WalletDepositRefundAdapter(mContext));

        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onRefundDeposit(mAdapter.getReason());
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new WalletDepositRefundPresenter(), this);

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public WalletDepositRefundContract.WalletDepositRefundBasePresenter getPresenter() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,
                R.style.dialog);
        final Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_error);
        LinearLayout linearLayout = window.findViewById(R.id.linear);
        Button btn_close = window.findViewById(R.id.btn_close);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        finish();
    }


    @Override
    public void onLoadReason(List<String> data) {
        mAdapter.update(data);
    }
}
