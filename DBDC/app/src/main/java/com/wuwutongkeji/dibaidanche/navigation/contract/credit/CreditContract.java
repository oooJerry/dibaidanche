package com.wuwutongkeji.dibaidanche.navigation.contract.credit;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.CreditEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletRecordEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public interface CreditContract {

    abstract class CreditBasePresenter extends BasePresenter<CreditContract.CreditBaseView> implements RefreshLayout.RefreshListener{

    }

    interface CreditBaseView extends BaseDependView<CreditContract.CreditBasePresenter> {

        void onShowCredit(String credit);

        void onFirstLoad();

        void onRefreshAndLoadMoreStop();

        void onLoadMoreEnable(boolean enable);

        void onRefresh(List<CreditEntity.DataEntity> data);

        void onLoadMore(List<CreditEntity.DataEntity> data);

    }
}
