package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import android.content.Context;
import android.content.Intent;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface WalletContract {

    abstract class WalletBasePresenter extends BasePresenter<WalletContract.WalletBaseView> {

        public abstract void onLoadFreeCard();

        public abstract void onLoadData();

        public abstract Intent onDepositIntent(Context mContext);

        public abstract Intent onFreeCardIntent(Context mContext);

    }

    interface WalletBaseView extends BaseDependView<WalletContract.WalletBasePresenter> {

        void onCardIntent(List<FreeCardEntity> list);

        void onShowCardTitle(String title);

        void onShowBalance(long balance);

        void onShowCouponCount(String count);

        void onShowPayDeposit(boolean isPayDeposit);
    }
}
