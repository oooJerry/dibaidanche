package com.wuwutongkeji.dibaidanche.navigation;

import android.os.Bundle;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.JourneyAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.journey.JourneyContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.journey.JourneyPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/25.
 */

public class JourneyActivity extends BaseToolbarActivity implements JourneyContract.JourneyBaseView{

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    JourneyAdapter mAdapter;
    JourneyContract.JourneyBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_journey;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("我的行程");
        refreshLayout.setAdapter(mAdapter = new JourneyAdapter(mContext));


    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new JourneyPresenter(),this);
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
    public void onRefresh(List<LockEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public void onLoadMore(List<LockEntity> data) {
        mAdapter.LoadMore(data);
    }

    @Override
    public JourneyContract.JourneyBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
