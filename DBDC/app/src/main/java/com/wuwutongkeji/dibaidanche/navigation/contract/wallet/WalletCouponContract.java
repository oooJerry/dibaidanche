package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.RefreshLayout;
import com.wuwutongkeji.dibaidanche.entity.WalletCouponEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/4.
 */

public interface WalletCouponContract {

    abstract class WalletCouponBasePresenter extends BasePresenter<WalletCouponContract.WalletCouponBaseView> implements RefreshLayout.RefreshListener{

    }

    interface WalletCouponBaseView extends BaseDependView<WalletCouponContract.WalletCouponBasePresenter> {

        void onFirstLoad();

        void onRefreshAndLoadMoreStop();

        void onLoadMoreEnable(boolean enable);

        void onRefresh(List<WalletCouponEntity.DataEntity> data);

        void onLoadMore(List<WalletCouponEntity.DataEntity> data);

        void showEmptyView(boolean isShow);
    }
}
