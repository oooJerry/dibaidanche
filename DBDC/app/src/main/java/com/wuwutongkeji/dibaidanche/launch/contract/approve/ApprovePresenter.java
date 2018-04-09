package com.wuwutongkeji.dibaidanche.launch.contract.approve;

import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;

/**
 * Created by Mr.Bai on 17/9/15.
 */

public class ApprovePresenter extends ApproveContract.ApproveBasePresenter {



    @Override
    public void onAuth(final String idCard, final String name) {

        if(TextUtil.isEmpty(name)){
            mDependView.showMsg("请输入姓名");
            return;
        }
        if(!TextUtil.isIdcard(idCard)){
            mDependView.showMsg("请输入正确的身份证号");
            return;
        }

        mNetDataManager.user_auth(idCard,name)
                .subscribe(new DefaultNetSubscriber<NetModel<LoginEntity>>(mDialog) {
                    @Override
                    public void onCompleted(NetModel<LoginEntity> netModel) {
                        if(netModel.isSuccess()){
                            LoginEntity entity = SharedPreferencesUtil.getUser();
                            entity.setAuthId(true);
                            entity.setName(netModel.getData().getName());
                            entity.setIdNumber(netModel.getData().getIdNumber());
                            SharedPreferencesUtil.saveUser(entity);
                            mDependView.onBusinessFinish(entity);
                        }else{
                            if("IDCARD_HAVEN_EXISTS".equals(netModel.getReturnCode())){
                                netModel.getData().setName(name);
                                netModel.getData().setIdNumber(idCard);
                                mDependView.onShowApproveFail(netModel.getData());
                            }else{
                                mDependView.showMsg(netModel.getDesc());
                            }
                        }
                    }
                });
    }
}
