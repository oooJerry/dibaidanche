package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.navigation.FreeCardActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mr.Bai on 17/10/3.
 */

public class WalletRechargePresenter extends WalletRechargeContract.WalletRechargeBasePresenter {

    @Override
    protected void onAttach() {
        super.onAttach();


        mNetDataManager.pay_depositAmount()
                .subscribe(new DefaultNetSubscriber<DepositEntity>(mDialog) {
                    @Override
                    public void onCompleted(DepositEntity entity) {
                        mDependView.onLoadAmount(entity);
                    }
                });

    }

    @Override
    public void onPayBalance(final PayManager.PayChannel payChannel, long amount) {

        mNetDataManager.pay_balance(String.valueOf(payChannel),amount)
                .subscribe(new DefaultNetSubscriber<String>(mDialog) {
                    @Override
                    public void onCompleted(String s) {
                        mDependView.onPay(payChannel,s);
                    }
                });

    }

    @Override
    public void onPay(final PayManager.PayChannel payChannel) {
        if (FreeCardActivity.freeCardTypeId.equals("0")) {
            mNetDataManager.pay_yearcard(String.valueOf(payChannel))
                    .subscribe(new DefaultNetSubscriber<String>(mDialog) {
                        @Override
                        public void onCompleted(String s) {
                            mDependView.onPay(payChannel, s);
                        }
                    });

        } else {
            mNetDataManager.pay_freecard(String.valueOf(payChannel), FreeCardActivity.freeCardTypeId)
                    .subscribe(new DefaultNetSubscriber<String>(mDialog) {
                        @Override
                        public void onCompleted(String s) {
                            mDependView.onPay(payChannel, s);
                        }
                    });
        }

    }

    @Override
    public PayManager.PayListener onPayListener() {
        return onPayListener;
    }

    @Override
    public void showMoreType(View btnMoreType,View btnWechat) {
        btnMoreType.setVisibility(View.GONE);
        btnWechat.setVisibility(View.VISIBLE);
    }

    PayManager.PayListener onPayListener = new PayManager.PayListener() {
        @Override
        public void onResult(boolean isSucess, String resultInfo) {
            if(isSucess){
                mDependView.onBusinessFinish(null);
            }else{
                mDependView.showMsg("支付失败,请重试");
            }
        }
    };
}
