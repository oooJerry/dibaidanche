package com.wuwutongkeji.dibaidanche.navigation.contract.credit;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.CreditEntity;
import com.wuwutongkeji.dibaidanche.navigation.adapter.CreditAdapter;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public class CreditPresenter extends CreditContract.CreditBasePresenter {

    private String pageNum = "0";

    private boolean isRefresh;

    private CreditAdapter mAdapter;

    public CreditPresenter(CreditAdapter mAdapter){
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        mDependView.onShowCredit(SharedPreferencesUtil.getUser().getCredit());

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

        mNetDataManager.credit_fetch(pageNum)
                .subscribe(new DefaultNetSubscriber<CreditEntity>(null) {
                    @Override
                    public void onCompleted(CreditEntity creditEntity) {

                        List<CreditEntity.DataEntity> data = creditEntity.getData();

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
                    }
                });
    }
}
