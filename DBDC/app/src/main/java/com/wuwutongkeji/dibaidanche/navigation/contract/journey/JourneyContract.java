package com.wuwutongkeji.dibaidanche.navigation.contract.journey;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface JourneyContract {

    abstract class JourneyBasePresenter extends BasePresenter<JourneyContract.JourneyBaseView> implements RefreshLayout.RefreshListener{

    }

    interface JourneyBaseView extends BaseDependView<JourneyContract.JourneyBasePresenter> {

        void onFirstLoad();

        void onRefreshAndLoadMoreStop();

        void onLoadMoreEnable(boolean enable);

        void onRefresh(List<LockEntity> data);

        void onLoadMore(List<LockEntity> data);
    }
}
