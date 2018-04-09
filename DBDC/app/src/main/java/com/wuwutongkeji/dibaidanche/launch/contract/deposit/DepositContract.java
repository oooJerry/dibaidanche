package com.wuwutongkeji.dibaidanche.launch.contract.deposit;

import android.view.View;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;

/**
 * Created by Mr.Bai on 2017/9/15.
 */

public interface DepositContract {

    abstract class DepositBasePresenter extends BasePresenter<DepositContract.DepositBaseView> {

        public abstract void loadPayAmount();

        public abstract void onPay(PayManager.PayChannel payChannel);

        public abstract PayManager.PayListener onPayListener();

        public abstract void showMoreType(View btnMoreType, View btnAliPay);
    }

    interface DepositBaseView extends BaseDependView<DepositContract.DepositBasePresenter> {

       void showMinBalance(String balance);

       void onPay(PayManager.PayChannel payChannel,String orderInfo);
    }
}
