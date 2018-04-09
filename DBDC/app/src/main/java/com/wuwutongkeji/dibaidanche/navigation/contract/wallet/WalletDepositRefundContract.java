package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface WalletDepositRefundContract {

    abstract class WalletDepositRefundBasePresenter extends BasePresenter<WalletDepositRefundContract.WalletDepositRefundBaseView> {

        public abstract void onRefundDeposit(String reason);
    }

    interface WalletDepositRefundBaseView extends BaseDependView<WalletDepositRefundContract.WalletDepositRefundBasePresenter> {

        void onLoadReason(List<String> data);
    }
}
