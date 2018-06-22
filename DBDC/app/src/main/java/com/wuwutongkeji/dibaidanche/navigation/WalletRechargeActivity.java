package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPayDialog;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletRechargeEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.WWalletRechargeAdapter;
import com.wuwutongkeji.dibaidanche.navigation.adapter.WalletRechargeAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.freecard.FreeCardContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.freecard.FreeCardPresenter;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletRechargeContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletRechargePresenter;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletRechargeActivity extends BaseToolbarActivity implements WalletRechargeContract.WalletRechargeBaseView {

    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.wechat_radio)
    RadioButton wechatRadio;
    @BindView(R.id.btn_wechat)
    LinearLayout btnWechat;
    @BindView(R.id.aliPay_radio)
    RadioButton aliPayRadio;
    @BindView(R.id.btn_moreType)
    LinearLayout btnMoreType;
    @BindView(R.id.btn_alipay)
    LinearLayout btnAlipay;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.btn_protocol)
    TextView btnProtocol;

    WalletRechargeAdapter mAdapter;
    WWalletRechargeAdapter mWAdapter;

    WalletRechargeContract.WalletRechargeBasePresenter mPresenter;
    //骑行卡 true  余额充值 false
    private boolean state = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_wallet_recharge;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("充值");

        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerview.setAdapter(mAdapter = new WalletRechargeAdapter(mContext, false));

        recyclerview1.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerview1.setAdapter(mWAdapter = new WWalletRechargeAdapter(mContext, true));

        btnProtocol.setText(Html.fromHtml(
                "点击充值即表示已阅读并同意" +
                        "<font color='#64AFF1'>《充值协议》</font><br />充值金额无法取现或退款"));

        btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatRadio.setChecked(true);
            }
        });

        btnAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPayRadio.setChecked(true);
            }
        });

        btnMoreType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showMoreType(btnMoreType, btnWechat);
            }
        });

        btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state) {
                    //购买骑行卡
                    mPresenter.onPay(aliPayRadio.isChecked() ?
                            PayManager.PayChannel.ALIPAY : PayManager.PayChannel.WECHATPAY);
                } else {
                    //充值余额
                    mPresenter.onPayBalance(aliPayRadio.isChecked() ?
                                    PayManager.PayChannel.ALIPAY : PayManager.PayChannel.WECHATPAY,
                            mAdapter.getSelectedData());
                }
            }
        });
        btnProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE, "充值协议");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.PAY_PROTOCOL);
                startActivity(intent);
            }
        });
        mWAdapter.setOnItemClickListener(new WWalletRechargeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                state = true;
                mAdapter.getSelectedState();
            }
        });
        mAdapter.setOnItemClickListener(new WalletRechargeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                state = false;
                mWAdapter.getSelectedState();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new WalletRechargePresenter(), this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void onLoadAmount(DepositEntity entity) {
        mAdapter.update(entity.getBalanceList());
        List<Long> longList = new ArrayList<>();
//        longList.add(entity.getYearCard());
        longList.add(entity.getSixMonthCard());
//        longList.add(entity.getSeasonCard());
//        longList.add(entity.getMonthCard());
        mWAdapter.update(longList);
    }

    @Override
    public void onPay(PayManager.PayChannel payChannel, String orderInfo) {
        if (payChannel == PayManager.PayChannel.ALIPAY) {
            PayManager.aliPay(orderInfo, this, mPresenter.onPayListener());
        } else if (payChannel == PayManager.PayChannel.WECHATPAY) {
            PayManager.weChatPay(this, orderInfo, mPresenter.onPayListener());
        }
    }

    @Override
    public WalletRechargeContract.WalletRechargeBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        WalletRechargeEntity entity = new WalletRechargeEntity();
        entity.blance = mAdapter.getSelectedData() + SharedPreferencesUtil.getUser().getBalance();
        EventBus.getDefault().post(entity);
        finish();
    }

}
