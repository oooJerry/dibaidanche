package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import com.wuwutongkeji.dibaidanche.base.BaseAppCompatActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.popup.DepositRefundDialog;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.QueryDepositEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositQueryEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class WalletDepositPresenter extends WalletDepositContract.WalletDepositBasePresenter {

    Long amount;

    @Override
    protected void onAttach() {
        super.onAttach();

        mNetDataManager.pay_queryDeposit()
                .subscribe(new DefaultNetSubscriber<QueryDepositEntity>(mDialog) {
                    @Override
                    public void onCompleted(QueryDepositEntity entity) {
                        amount = new Long(entity.getAmount());
                        mDependView.onShowDeposit(TextUtil.getMoneyByPenny(entity.getAmount()));
                    }
                });
    }

    @Override
    public void onDepositRefund(final BaseAppCompatActivity appCompatActivity) {

        WalletDepositQueryEntity queryEntity = new WalletDepositQueryEntity();

        EventBus.getDefault().post(queryEntity);

        AppConfig.LockType lockType = null;

        if(null != queryEntity){
            lockType = queryEntity.getLockType();
        }

        if(null != lockType && (lockType == AppConfig.LockType.LOCK_OPENING
                || lockType == AppConfig.LockType.LOCK_OPENED_SUCCESS)){
            mDependView.showMsg("正在骑行中,请结束订单后再退款");
            return;
        }

        if(null == amount){
            mDependView.showMsg("请重新加载页面!");
            return;
        }
//        if(amount == 9900){
//            new DepositRefundDialog().show(appCompatActivity.getSupportFragmentManager());
//        }else{
            mDependView.onStartActivity(AppIntent.getWalletDepositRefundActivity(appCompatActivity));
//        }
    }
}
