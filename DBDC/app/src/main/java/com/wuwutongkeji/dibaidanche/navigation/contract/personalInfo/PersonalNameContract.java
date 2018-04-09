package com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public interface PersonalNameContract {

    abstract class PersonalNameBasePresenter extends BasePresenter<PersonalNameContract.PersonalNameBaseView> {

        public abstract void onSaveName(String name);
    }

    interface PersonalNameBaseView extends BaseDependView<PersonalNameContract.PersonalNameBasePresenter> {


    }
}
