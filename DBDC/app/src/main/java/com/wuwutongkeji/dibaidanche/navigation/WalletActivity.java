package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositRefundEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletRechargeEntity;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 2017/9/25.
 */

public class WalletActivity extends BaseToolbarActivity implements WalletContract.WalletBaseView {

    @BindView(R.id.btn_balance)
    TextView btnBalance;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_couponCount)
    TextView tvCouponCount;
    @BindView(R.id.btn_coupon)
    LinearLayout btnCoupon;
    @BindView(R.id.btn_deposit)
    LinearLayout btnDeposit;
    @BindView(R.id.tv_isPayDeposit)
    TextView tvIsPayDeposit;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.tv_cardtitle)
    TextView tvCardtitle;
    @BindView(R.id.btn_card)
    View btnCard;

    WalletContract.WalletBasePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_wallet;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("我的钱包");
        setRightBtnTitle("明细");

        if (SharedPreferencesUtil.getUser().getBalance() < 0) {
            btnRecharge.setText("充值");
        } else {
            btnRecharge.setText("购买骑行卡");
        }

        btnBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE, "余额说明");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.BALANCE_PROTOCOL);
                startActivity(intent);
            }
        });
        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getWalletCouponActivity(mContext));
            }
        });

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mPresenter.onDepositIntent(mContext));
            }
        });

        btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEntity loginEntity = SharedPreferencesUtil.getUser();
                if (SharedPreferencesUtil.getUser().getBalance() < 0) {
                    Intent intent = new Intent(mContext, WalletRechargeActivity.class);
                    startActivity(intent);
                } else {
                    if (!loginEntity.isAuthId()) {
                        startActivity(AppIntent.getApproveActivity(mContext));
                    }else {
                        startActivity(AppIntent.getFreeCardActivity(mContext));
                    }
                }
            }
        });

    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new WalletPresenter(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onShowPayDeposit(SharedPreferencesUtil.getUser().isPayDeposit());
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    protected boolean showRightTextView() {
        return true;
    }

    @Override
    protected void onRightBtnClickListener(View view) {
        super.onRightBtnClickListener(view);
        startActivity(AppIntent.getWalletDetailActivity(mContext));
    }


    @Override
    public WalletContract.WalletBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWalletRecharge(WalletRechargeEntity entity) {
        onShowBalance(entity.blance);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFreeCardChanged(FreeCardEntity entity) {
        mPresenter.onLoadFreeCard();
    }

    @Override
    public void onCardIntent(final List<FreeCardEntity> list) {
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mPresenter.onFreeCardIntent(mContext);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onShowCardTitle(String title) {
        tvCardtitle.setText(title);
    }

    @Override
    public void onShowBalance(long balance) {
        tvBalance.setText(TextUtil.getMoneyByPenny(balance));
    }

    @Override
    public void onShowCouponCount(String count) {
        tvCouponCount.setText(count + " 张");
    }

    @Override
    public void onShowPayDeposit(boolean isPayDeposit) {
        if (!isPayDeposit) {
            tvIsPayDeposit.setText("未缴纳押金");
            tvIsPayDeposit.setTextColor(Color.parseColor("#FF8400"));
        } else {
            tvIsPayDeposit.setTextColor(Color.parseColor("#333333"));
            tvIsPayDeposit.setText("已交押金");
        }
    }
}
