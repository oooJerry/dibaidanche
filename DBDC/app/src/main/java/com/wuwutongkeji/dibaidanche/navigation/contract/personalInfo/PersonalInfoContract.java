package com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface PersonalInfoContract {

    abstract class PersonalInfoBasePresenter extends BasePresenter<PersonalInfoContract.PersonalInfoBaseView> {

        public abstract void onLoadUserInfo();

        public abstract void uploadFile(String file);

    }

    interface PersonalInfoBaseView extends BaseDependView<PersonalInfoContract.PersonalInfoBasePresenter> {

        void onLoadUserInfo(LoginEntity entity);

        void onLoadUserInfoSucc(String url);
    }

}
