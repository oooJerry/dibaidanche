package com.wuwutongkeji.dibaidanche.navigation.contract.invitefriend;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.ShareEntity;

/**
 * Created by Mr.Bai on 17/10/4.
 */

public interface InviteFriendContract {

    abstract class InviteFriendBasePresenter extends BasePresenter<InviteFriendContract.InviteFriendBaseView> {

    }

    interface InviteFriendBaseView extends BaseDependView<InviteFriendContract.InviteFriendBasePresenter> {

        void onLoadData(ShareEntity entity);
    }
}
