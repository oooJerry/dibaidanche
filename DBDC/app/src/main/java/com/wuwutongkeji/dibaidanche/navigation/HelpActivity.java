package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.entity.HelpEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.HelpAdapter;
import com.wuwutongkeji.dibaidanche.navigation.contract.help.HelpContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.help.HelpPresenter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/26.
 */

public class HelpActivity extends BaseToolbarActivity implements HelpContract.HelpBaseView {

    @BindView(R.id.refreshLayout)
    RecyclerView refreshLayout;

    HelpAdapter mAdapter;

    HelpContract.HelpBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_help;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("使用指南");

        refreshLayout.setAdapter(mAdapter = new HelpAdapter(mContext));
        refreshLayout.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setOnHelpListener(new HelpAdapter.HelpListener() {
            @Override
            public void onItem(HelpEntity entity) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE,entity.getTitle());
                intent.putExtra(BrowserActivity.KEY_DATA,entity.getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new HelpPresenter(),this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void loadData(List<HelpEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public HelpContract.HelpBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}
