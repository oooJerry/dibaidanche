package com.wuwutongkeji.dibaidanche.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPayDialog;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;
import com.wuwutongkeji.dibaidanche.navigation.contract.freecard.FreeCardContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.freecard.FreeCardPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public class FreeCardActivity extends BaseToolbarActivity implements FreeCardContract.FreeCardBaseView {

    @BindView(R.id.tv_cardtitle)
    TextView tvCardtitle;
    @BindView(R.id.tv_cardPrice)
    TextView tvCardPrice;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    FreeCardPayDialog freeCardPayDialog;
    FreeCardPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_freecard;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("年卡");
        freeCardPayDialog = new FreeCardPayDialog();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {freeCardPayDialog.show(getSupportFragmentManager());
            }
        });
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new FreeCardPresenter(),this);
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
    public void onShowCardPrice(String title) {
        tvCardPrice.setText(title);
    }

    @Override
    public void onShowSubmitTxt(String txt) {
        btnSubmit.setText(txt);
    }

    @Override
    public void onPay(PayManager.PayChannel payChannel, String orderInfo) {
        if(payChannel == PayManager.PayChannel.ALIPAY){
            PayManager.aliPay(orderInfo,this,mPresenter.onPayListener());
        }else if(payChannel == PayManager.PayChannel.WECHATPAY){
            PayManager.weChatPay(this,orderInfo,mPresenter.onPayListener());
        }
    }

    @Override
    public FreeCardContract.FreeCardBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
