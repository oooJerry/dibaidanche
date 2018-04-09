package com.wuwutongkeji.dibaidanche.launch.contract.login;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;

/**
 * Created by Mr.Bai on 17/9/14.
 */

public interface LoginContract {

    abstract class LoginBasePresenter extends BasePresenter<LoginContract.LoginBaseView> {

        public abstract boolean timerIsRun();

        public abstract void sendVerifyCode(String mobile);

        public abstract void login(String mobile,String verifyCode,String inviteCode);

        public abstract void onDestroyTimer();

    }

    interface LoginBaseView extends BaseDependView<LoginContract.LoginBasePresenter> {

        void timerofCode(boolean enable,String timeCode);

    }
}
