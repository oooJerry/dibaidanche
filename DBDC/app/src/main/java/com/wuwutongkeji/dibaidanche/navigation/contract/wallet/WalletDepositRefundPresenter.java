package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositRefundEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class WalletDepositRefundPresenter extends WalletDepositRefundContract.WalletDepositRefundBasePresenter {

    @Override
    protected void onAttach() {
        super.onAttach();

        mNetDataManager.pay_refundReason()
                .subscribe(new DefaultNetSubscriber<List<String>>(mDialog) {
                    @Override
                    public void onCompleted(List<String> strings) {
                        mDependView.onLoadReason(strings);
                    }
                });
    }

    @Override
    public void onRefundDeposit(String reason) {
        mNetDataManager.pay_refundDeposit(reason)
                .subscribe(new DefaultNetSubscriber<String>(mDialog) {
                    @Override
                    public void onCompleted(String s) {
                        LoginEntity loginEntity = SharedPreferencesUtil.getUser();
                        loginEntity.setPayDeposit(false);
                        SharedPreferencesUtil.saveUser(loginEntity);
                        mDependView.onBusinessFinish(null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mDependView.getPresenter();
                    }
                });
    }
}
