package com.wuwutongkeji.dibaidanche.bike.contract.usingFinish;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.ConsumeEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.UserInfoEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mr.Bai on 17/10/5.
 */

public class BikeUsingFinishPresenter extends BikeUsingFinishContract.BikeUsingFinishBasePresenter {

    private String consumeId;

    public BikeUsingFinishPresenter(String consumeId){
        this.consumeId = consumeId;
    }

    @Override
    protected void onAttach() {
        super.onAttach();


        mNetDataManager.consume_queryConsumeState(consumeId)
                .subscribe(new DefaultNetSubscriber<ConsumeEntity>(mDialog) {
                    @Override
                    public void onCompleted(ConsumeEntity consumeEntity) {
                        mDependView.loadData(consumeEntity);

                        LoginEntity user = consumeEntity.getUser();

                        if(null == user){
                            return;
                        }
                        LoginEntity entity = SharedPreferencesUtil.getUser();
                        entity.setCredit(user.getCredit());
                        entity.setBalance(user.getBalance());
                        entity.setDiscountCoupon(user.getDiscountCoupon());
                        SharedPreferencesUtil.saveUser(entity);
                        EventBus.getDefault().post(user);
                    }
                });
    }
}
