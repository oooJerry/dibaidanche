package com.wuwutongkeji.dibaidanche.launch.contract.approve;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

/**
 * Created by Mr.Bai on 17/9/15.
 */

public interface ApproveContract {


    abstract class ApproveBasePresenter extends BasePresenter<ApproveContract.ApproveBaseView> {

        public abstract void onAuth(String idCard,String name);

    }

    interface ApproveBaseView extends BaseDependView<ApproveContract.ApproveBasePresenter> {

        void onShowApproveFail(LoginEntity entity);

    }

}
