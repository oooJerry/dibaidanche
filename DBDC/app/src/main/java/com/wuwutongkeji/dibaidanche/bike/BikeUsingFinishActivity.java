package com.wuwutongkeji.dibaidanche.bike;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.bike.contract.usingFinish.BikeUsingFinishContract;
import com.wuwutongkeji.dibaidanche.bike.contract.usingFinish.BikeUsingFinishPresenter;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.ConsumeEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.UserInfoEntity;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 17/9/20.
 */

public class BikeUsingFinishActivity extends BaseToolbarActivity
        implements BikeUsingFinishContract.BikeUsingFinishBaseView{

    public static String KEY_DATA = "KEY_DATA";

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_blance)
    TextView tvBlance;
    @BindView(R.id.btn_blance)
    LinearLayout btnBlance;
    @BindView(R.id.tv_couponCount)
    TextView tvCouponCount;
    @BindView(R.id.btn_coupon)
    LinearLayout btnCoupon;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    BikeUsingFinishContract.BikeUsingFinishBasePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bike_using_finish;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("骑行结束");

        btnBlance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getWalletActivity(mContext));
            }
        });

        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getWalletCouponActivity(mContext));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new BikeUsingFinishPresenter(getIntent().getStringExtra(KEY_DATA)),this);
    }

    @Override
    public void loadData(ConsumeEntity data) {

        tvMoney.setText(TextUtil.getMoneyByPenny(data.getConsumeBalance()));
        tvTime.setText(data.getRideTime());

        LoginEntity user = data.getUser();

        if(null == user){
            return;
        }
        tvBlance.setText(TextUtil.getMoneyByPenny(user.getBalance()) + "元");
        tvCouponCount.setText(user.getDiscountCoupon() + "张");
    }

    @Override
    public BikeUsingFinishContract.BikeUsingFinishBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
