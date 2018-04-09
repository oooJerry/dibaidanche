package com.wuwutongkeji.dibaidanche.launch.contract.appeal;

import android.content.Context;

import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

/**
 * Created by Mr.Bai on 17/9/16.
 */

public interface AppealContract {

    abstract class AppealBasePresenter extends BasePresenter<AppealContract.AppealBaseView> {

        public abstract void onForntPic(String path);

        public abstract void onBackPic(String path);

        public abstract void onAuth(LoginEntity loginEntity);

    }

    interface AppealBaseView extends BaseDependView<AppealContract.AppealBasePresenter> {

        void onShowFrontPic(String path);

        void onShowBackPic(String path);
    }
}
