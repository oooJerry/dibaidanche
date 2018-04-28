package com.wuwutongkeji.dibaidanche.navigation.contract.wallet;

import android.content.Context;
import android.content.Intent;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPublicityDialog;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.UserInfoEntity;
import com.wuwutongkeji.dibaidanche.launch.contract.main.MainContract;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class WalletPresenter extends WalletContract.WalletBasePresenter {


    @Override
    protected void onAttach() {
        super.onAttach();

        onLoadFreeCard();
        onLoadData();
    }

    @Override
    public void onLoadFreeCard() {
        mNetDataManager.freeCard_list("0")
                .subscribe(new DefaultNetSubscriber<NetModel<List<FreeCardEntity>>>(mDialog) {
                    @Override
                    public void onCompleted(NetModel<List<FreeCardEntity>> netModel) {

                        Long remainTime = null;

                        for (FreeCardEntity entity : netModel.getData()) {
                            if (entity.getRemainTime() > 0) {
                                if (null == remainTime) {
                                    remainTime = new Long(0);
                                }
                                remainTime += entity.getRemainTime();
                            }
                        }

                        if (null == remainTime) {
                            mDependView.onShowCardTitle("未使用");
                        } else {
                            mDependView.onShowCardTitle("剩余" + DateUtil.long2Day(remainTime.longValue()) + "天");
                        }

                        mDependView.onCardIntent(netModel.getData());
                    }
                });
    }

    @Override
    public void onLoadData() {
        mNetDataManager.user_info_filter()
                .subscribe(new DefaultNetSubscriber<UserInfoEntity>(mDialog) {
                    @Override
                    public void onCompleted(UserInfoEntity userInfoEntity) {
                        LoginEntity entity = userInfoEntity.getUser();
                        if (null == entity) {
                            return;
                        }
                        mDependView.onShowBalance(entity.getBalance());
                        mDependView.onShowCouponCount(entity.getDiscountCoupon());
                        mDependView.onShowPayDeposit(entity.isPayDeposit());
                    }
                });
    }

    @Override
    public Intent onDepositIntent(Context mContext) {
        return SharedPreferencesUtil.getUser().isPayDeposit() ?
                AppIntent.getWalletDepositActivity(mContext) :
                AppIntent.getDepositActivity(mContext);
    }

    @Override
    public Intent onFreeCardIntent(Context mContext) {
        LoginEntity loginEntity = SharedPreferencesUtil.getUser();
        if (null == loginEntity) {
            EventBus.getDefault().post(AppConfig.NO_LOGIN);
            return null;
        }
        if (!loginEntity.isAuthId()) {
            return AppIntent.getApproveActivity(mContext);
        }
        if (SharedPreferencesUtil.getUser().getBalance() < 0) {
            return AppIntent.getWalletRechargeActivity(mContext);
        }
        return AppIntent.getFreeCardActivity(mContext);
    }

}
