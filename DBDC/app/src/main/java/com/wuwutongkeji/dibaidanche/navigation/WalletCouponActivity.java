package com.wuwutongkeji.dibaidanche.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.WalletCouponEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.WalletCouponAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletCouponContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.WalletCouponPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletCouponActivity extends BaseToolbarActivity implements WalletCouponContract.WalletCouponBaseView{


    @BindView(R.id.layout_emptyView)
    LinearLayout layoutEmptyView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    WalletCouponAdapter mAdapter;

    WalletCouponContract.WalletCouponBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_wallet_coupon;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("我的用车券");

        refreshLayout.setAdapter(mAdapter = new WalletCouponAdapter(mContext));
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new WalletCouponPresenter(mAdapter),this);
        refreshLayout.setOnRefreshListener(mPresenter);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void onFirstLoad() {
        refreshLayout.firstRefresh();
    }

    @Override
    public void onRefreshAndLoadMoreStop() {
        refreshLayout.refreshAndLoadMoreStop();
    }

    @Override
    public void onLoadMoreEnable(boolean enable) {
        refreshLayout.setLoadMoreEnabled(enable);
    }

    @Override
    public void onRefresh(List<WalletCouponEntity.DataEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public void onLoadMore(List<WalletCouponEntity.DataEntity> data) {
        mAdapter.LoadMore(data);
    }

    @Override
    public void showEmptyView(boolean isShow) {
        layoutEmptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public WalletCouponContract.WalletCouponBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
