package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.WalletCouponEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.WalletCouponAdapter;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/4.
 */

public class WalletCouponPresenter extends WalletCouponContract.WalletCouponBasePresenter{

    private String pageNum = "0";

    private boolean isRefresh;

    private WalletCouponAdapter mAdapter;

    public WalletCouponPresenter(WalletCouponAdapter mAdapter) {
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
        onQueryCouponList();
    }

    @Override
    public void onLoadMore(RefreshLayout layout) {
        isRefresh = false;
        onQueryCouponList();
    }

    void onQueryCouponList(){

        mNetDataManager.coupon_fetch(pageNum)
                .subscribe(new DefaultNetSubscriber<WalletCouponEntity>(null) {
                    @Override
                    public void onCompleted(WalletCouponEntity walletCouponEntity) {

                        List<WalletCouponEntity.DataEntity> data = walletCouponEntity.getData();

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
