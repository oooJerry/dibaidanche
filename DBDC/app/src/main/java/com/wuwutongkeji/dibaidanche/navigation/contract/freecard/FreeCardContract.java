package com.wuwutongkeji.dibaidanche.navigation.contract.freecard;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public interface FreeCardContract {

    abstract class FreeCardBasePresenter extends BasePresenter<FreeCardBaseView> {

        public abstract void onLoadData();

        public abstract void onPay(PayManager.PayChannel payChannel);

        public abstract PayManager.PayListener onPayListener();
    }

    interface FreeCardBaseView extends BaseDependView<FreeCardBasePresenter> {

        void onShowCardTitle(String title);

        void onShowCardPrice(String title);

        void onShowSubmitTxt(String txt);

        void onPay(PayManager.PayChannel payChannel, String orderInfo);
    }
}
