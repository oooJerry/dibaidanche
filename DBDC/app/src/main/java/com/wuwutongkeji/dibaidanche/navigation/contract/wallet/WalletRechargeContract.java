package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import android.view.View;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public interface WalletRechargeContract {

    abstract class WalletRechargeBasePresenter extends BasePresenter<WalletRechargeContract.WalletRechargeBaseView> {

        public abstract void onPayBalance(PayManager.PayChannel payChannel,long amount);

        public abstract PayManager.PayListener onPayListener();

        public abstract void showMoreType(View btnMoreType,View btnAliPay);
    }

    interface WalletRechargeBaseView extends BaseDependView<WalletRechargeContract.WalletRechargeBasePresenter> {

        void onLoadAmount(DepositEntity entity);

        void onPay(PayManager.PayChannel payChannel, String orderInfo);

    }
}
