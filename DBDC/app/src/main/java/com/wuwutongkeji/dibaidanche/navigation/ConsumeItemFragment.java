package com.wuwutongkeji.dibaidanche.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseFragment;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.ConsumeItemAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.ConsumeItemContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.wallet.ConsumeItemPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/25.
 */

public class ConsumeItemFragment extends BaseFragment implements ConsumeItemContract.ConsumeItemBaseView {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.layout_emptyView)
    LinearLayout layoutEmptyView;

    ConsumeItemContract.ConsumeItemBasePresenter mPresenter;

    ConsumeItemAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation_wallet_item_consume;
    }

    @Override
    protected void initToolbar(View ChildView, Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(View ChildView, Bundle savedInstanceState) {

        refreshLayout.setAdapter(mAdapter = new ConsumeItemAdapter(mContext));

    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new ConsumeItemPresenter(mAdapter), this);
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
    public void onRefresh(List<LockEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public void onLoadMore(List<LockEntity> data) {
        mAdapter.LoadMore(data);
    }

    @Override
    public void showEmptyView(boolean isShow) {
        layoutEmptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public ConsumeItemContract.ConsumeItemBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }

}
