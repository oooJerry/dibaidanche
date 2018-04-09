package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface ConsumeItemContract {

    abstract class ConsumeItemBasePresenter extends BasePresenter<ConsumeItemContract.ConsumeItemBaseView> implements RefreshLayout.RefreshListener{

    }

    interface ConsumeItemBaseView extends BaseDependView<ConsumeItemContract.ConsumeItemBasePresenter> {

        void onFirstLoad();

        void onRefreshAndLoadMoreStop();

        void onLoadMoreEnable(boolean enable);

        void onRefresh(List<LockEntity> data);

        void onLoadMore(List<LockEntity> data);

        void showEmptyView(boolean isShow);
    }
}
