package com.wuwutongkeji.dibaidanche.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPayDialog;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.WWalletRechargeAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.freecard.FreeCardContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.freecard.FreeCardPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public class FreeCardActivity extends BaseToolbarActivity implements FreeCardContract.FreeCardBaseView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_cardtitle)
    TextView tvCardtitle;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    FreeCardPayDialog freeCardPayDialog;
    FreeCardPresenter mPresenter;

    public static String freeCardTypeId = "0";

    WWalletRechargeAdapter mWAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_freecard;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("骑行卡");
        freeCardPayDialog = new FreeCardPayDialog();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeCardPayDialog.show(getSupportFragmentManager());
            }
        });

        //freeCardTypeId = 0:年卡,1:月卡,2:季卡,3:半年卡
        // 默认选年卡
//      freeCardTypeId = "0";
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerview.setAdapter(mWAdapter = new WWalletRechargeAdapter(mContext, true));

    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new FreeCardPresenter(), this);
        freeCardPayDialog.setOnFreeCardListener(mPresenter);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void onShowCardTitle(String title) {
        tvCardtitle.setText(title);
    }

    @Override
    public void onShowCardPrice(DepositEntity title) {
        //仅留半年卡充值
        List<Long> longList = new ArrayList<>();
        longList.add(title.getYearCard());
        longList.add(title.getSixMonthCard());
//        longList.add(entity.getSeasonCard());
//        longList.add(entity.getMonthCard());
        mWAdapter.update(longList);
//        tvCardPrice.setText("365天=" + ((double)title.getYearCard() / 100) + "元");
//        tvCardPrice1.setText("180天=" + ((double) title.getSixMonthCard() / 100) + "元");
//        tvCardPrice2.setText("90天=" + ((double)title.getSeasonCard() / 100) + "元");
//        tvCardPrice3.setText("30天=" + ((double)title.getMonthCard() / 100) + "元");
        // 判断是否交押金
//        if (SharedPreferencesUtil.getUser().isPayDeposit()) {
//        } else {
//            AppIntent.getDepositActivity(mContext);
//        }
//        finish();
    }

    @Override
    public void onShowSubmitTxt(String txt) {
        btnSubmit.setText(txt);
    }

    @Override
    public void onPay(PayManager.PayChannel payChannel, String orderInfo) {
        System.out.println("支付宝支付:" + orderInfo);
        if (payChannel == PayManager.PayChannel.ALIPAY) {
            PayManager.aliPay(orderInfo, this, mPresenter.onPayListener());
        } else if (payChannel == PayManager.PayChannel.WECHATPAY) {
            PayManager.weChatPay(this, orderInfo, mPresenter.onPayListener());
        }
    }

    @Override
    public FreeCardContract.FreeCardBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
