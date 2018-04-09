package com.wuwutongkeji.dibaidanche.base;

import java.io.Serializable;

/**
 * Created by Mr.Bai on 2017/9/11.
 */
public interface BaseDependView<P extends BasePresenter> {

    P getPresenter();

    void onBusinessFinish(Serializable serializable);

    void showMsg(String msg);
}
