package com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class PersonalNamePresenter extends PersonalNameContract.PersonalNameBasePresenter {

    @Override
    public void onSaveName(final String name) {

        if(!TextUtil.checkNikeName(name)){
            mDependView.showMsg("请输入正确的昵称");
            return;
        }
        mNetDataManager.user_update(name,null)
                .subscribe(new DefaultNetSubscriber<LoginEntity>(mDialog) {
                    @Override
                    public void onCompleted(LoginEntity entity) {
                        LoginEntity loginEntity = SharedPreferencesUtil.getUser();
                        loginEntity.setNickname(name);
                        SharedPreferencesUtil.saveUser(loginEntity);
                        EventBus.getDefault().post(loginEntity);
                        mDependView.onBusinessFinish(null);
                    }
                });
    }
}
