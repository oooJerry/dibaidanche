package com.wuwutongkeji.dibaidanche.navigation.contract.journey;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class JourneyPresenter extends JourneyContract.JourneyBasePresenter {

    private int pageNum = 1;

    private boolean isRefresh;

    @Override
    protected void onAttach() {
        super.onAttach();
        mDependView.onFirstLoad();
    }

    @Override
    public void onRefresh(RefreshLayout layout) {
        isRefresh = true;
        pageNum = 1;
        onQueryConsumeList();
    }

    @Override
    public void onLoadMore(RefreshLayout layout) {
        pageNum += 1;
        isRefresh = false;
        onQueryConsumeList();
    }

    void onQueryConsumeList(){

        mNetDataManager.consume_queryConsumeList(pageNum)
                .subscribe(new DefaultNetSubscriber<List<LockEntity>>(null) {
                    @Override
                    public void onCompleted(List<LockEntity> journeyEntities) {
                        mDependView.onRefreshAndLoadMoreStop();
                        mDependView.onLoadMoreEnable(!journeyEntities.isEmpty());
                        if(isRefresh){
                            mDependView.onRefresh(journeyEntities);
                        }else {
                            mDependView.onLoadMore(journeyEntities);
                        }
                    }
                });
    }
}
