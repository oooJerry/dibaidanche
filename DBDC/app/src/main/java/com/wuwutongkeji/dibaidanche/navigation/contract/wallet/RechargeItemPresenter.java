package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.WalletRecordEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.RechargeItemAdapter;

import java.util.List;


/**
 * Created by Mr.Bai on 17/10/2.
 */

public class RechargeItemPresenter extends RechargeItemContract.RechargeItemBasePresenter {

    private String pageNum = "0";

    private boolean isRefresh;

    private RechargeItemAdapter mAdapter;

    public RechargeItemPresenter(RechargeItemAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        mDependView.onFirstLoad();
    }

    @Override
    public void onRefresh(RefreshLayout layout) {
        isRefresh = true;
        pageNum = "0";
        onQueryRechargeList();
    }

    @Override
    public void onLoadMore(RefreshLayout layout) {
        isRefresh = false;
        onQueryRechargeList();
    }

    void onQueryRechargeList(){

        mNetDataManager.pay_record(pageNum)
                .subscribe(new DefaultNetSubscriber<WalletRecordEntity>(null) {
                    @Override
                    public void onCompleted(WalletRecordEntity walletRecordEntity) {

                        List<WalletRecordEntity.DataEntity> data = walletRecordEntity.getData();

                        boolean isEmpty = data.isEmpty();

                        mDependView.onRefreshAndLoadMoreStop();
                        mDependView.onLoadMoreEnable(!isEmpty);

                        if(isRefresh){
                            mDependView.onRefresh(data);
                        }else {
                            mDependView.onLoadMore(data);
                        }
                        if(!isEmpty){
                            pageNum = data.get(data.size() - 1).getId();
                        }
                        mDependView.showEmptyView(mAdapter.getData().isEmpty());
                    }
                });
    }
}
