package com.wuwutongkeji.dibaidanche.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseFragment;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.WalletRecordEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.RechargeItemAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.RechargeItemContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.RechargeItemPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public class RechargeItemFragment extends BaseFragment implements RechargeItemContract.RechargeItemBaseView {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.layout_emptyView)
    LinearLayout layoutEmptyView;

    RechargeItemContract.RechargeItemBasePresenter mPresenter;

    RechargeItemAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation_wallet_item_recharge;
    }

    @Override
    protected void initToolbar(View ChildView, Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(View ChildView, Bundle savedInstanceState) {

        refreshLayout.setAdapter(mAdapter = new RechargeItemAdapter(mContext));

    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new RechargeItemPresenter(mAdapter), this);
        refreshLayout.setOnRefreshListener(mPresenter);
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
    public void onRefresh(List<WalletRecordEntity.DataEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public void onLoadMore(List<WalletRecordEntity.DataEntity> data) {
        mAdapter.LoadMore(data);
    }

    @Override
    public void showEmptyView(boolean isShow) {
        layoutEmptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public RechargeItemContract.RechargeItemBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }

}
