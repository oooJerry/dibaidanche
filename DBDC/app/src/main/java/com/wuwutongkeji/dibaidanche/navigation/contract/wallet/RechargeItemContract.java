package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.WalletRecordEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface RechargeItemContract {

    abstract class RechargeItemBasePresenter extends BasePresenter<RechargeItemContract.RechargeItemBaseView> implements RefreshLayout.RefreshListener{

    }

    interface RechargeItemBaseView extends BaseDependView<RechargeItemContract.RechargeItemBasePresenter> {

        void onFirstLoad();

        void onRefreshAndLoadMoreStop();

        void onLoadMoreEnable(boolean enable);

        void onRefresh(List<WalletRecordEntity.DataEntity> data);

        void onLoadMore(List<WalletRecordEntity.DataEntity> data);

        void showEmptyView(boolean isShow);
    }
}
