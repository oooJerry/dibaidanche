package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.CreditEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletRecordEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.CreditAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.credit.CreditContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.credit.CreditPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class CreditActivity extends BaseToolbarActivity implements CreditContract.CreditBaseView{

    @BindView(R.id.tv_credit)
    TextView tvCredit;
    @BindView(R.id.btn_creditRule)
    Button btnCreditRule;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    CreditContract.CreditBasePresenter mPresenter;

    CreditAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_credit;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("我的信用");

        refreshLayout.setAdapter(mAdapter = new CreditAdapter(mContext));

        btnCreditRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE,"信用分规则");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.CREDIT_RULE);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new CreditPresenter(mAdapter),this);

        refreshLayout.setOnRefreshListener(mPresenter);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void onShowCredit(String credit) {
        tvCredit.setText(credit);
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
    public void onRefresh(List<CreditEntity.DataEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public void onLoadMore(List<CreditEntity.DataEntity> data) {
        mAdapter.LoadMore(data);
    }


    @Override
    public CreditContract.CreditBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
