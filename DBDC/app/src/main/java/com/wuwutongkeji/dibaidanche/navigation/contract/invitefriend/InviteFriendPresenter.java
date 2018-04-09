package com.wuwutongkeji.dibaidanche.navigation.contract.invitefriend;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.entity.ShareEntity;

/**
 * Created by Mr.Bai on 17/10/4.
 */

public class InviteFriendPresenter extends InviteFriendContract.InviteFriendBasePresenter {


    @Override
    protected void onAttach() {
        super.onAttach();

        mNetDataManager.share_param()
                .subscribe(new DefaultNetSubscriber<ShareEntity>(mDialog) {
                    @Override
                    public void onCompleted(ShareEntity entity) {
                        mDependView.onLoadData(entity);
                    }
                });
    }
}
