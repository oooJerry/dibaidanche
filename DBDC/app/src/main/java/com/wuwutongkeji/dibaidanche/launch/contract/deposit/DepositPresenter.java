package com.wuwutongkeji.dibaidanche.launch.contract.deposit;

import android.view.View;

import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

/**
 * Created by Mr.Bai on 2017/9/15.
 */

public class DepositPresenter extends DepositContract.DepositBasePresenter {

    @Override
    protected void onAttach() {
        super.onAttach();
        loadPayAmount();
    }

    @Override
    public void loadPayAmount() {
        mNetDataManager.pay_depositAmount()
                .subscribe(new DefaultNetSubscriber<DepositEntity>(mDialog) {
                    @Override
                    public void onCompleted(DepositEntity depositEntity) {
                        mDependView.showMinBalance(TextUtil.getMoneyByPenny(depositEntity.getDeposit()));
                    }
                });
    }

    @Override
    public void onPay(final PayManager.PayChannel payChannel) {

        mNetDataManager.pay_depositChannel(String.valueOf(payChannel))
                .subscribe(new DefaultNetSubscriber<String>(mDialog) {
                    @Override
                    public void onCompleted(String s) {
                        mDependView.onPay(payChannel,s);
                    }
                });

    }

    @Override
    public PayManager.PayListener onPayListener() {
        return onPayListener;
    }

    @Override
    public void showMoreType(View btnMoreType, View btnAliPay) {
        btnMoreType.setVisibility(View.GONE);
        btnAliPay.setVisibility(View.VISIBLE);
    }


    PayManager.PayListener onPayListener = new PayManager.PayListener() {
        @Override
        public void onResult(boolean isSucess, String resultInfo) {
            if(isSucess){
                LoginEntity entity = SharedPreferencesUtil.getUser();
                entity.setPayDeposit(true);
                SharedPreferencesUtil.saveUser(entity);
                mDependView.onBusinessFinish(entity);
            }else{
                mDependView.showMsg("支付失败,请重试");
            }
        }
    };
}
