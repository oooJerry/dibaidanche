package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import android.content.Intent;

import com.wuwutongkeji.dibaidanche.base.BaseAppCompatActivity;
import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface WalletDepositContract {

    abstract class WalletDepositBasePresenter extends BasePresenter<WalletDepositContract.WalletDepositBaseView> {

        public abstract void onDepositRefund(BaseAppCompatActivity appCompatActivity);
    }

    interface WalletDepositBaseView extends BaseDependView<WalletDepositContract.WalletDepositBasePresenter> {

        void onShowDeposit(String deposit);

        void onStartActivity(Intent intent);
    }
}
