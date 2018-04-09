package com.wuwutongkeji.dibaidanche.launch.contract.appeal;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.HttpUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.UploadFileEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Bai on 17/9/16.
 */

public class AppealPresenter extends AppealContract.AppealBasePresenter {

    String frontPath;

    String backPath;


    @Override
    public void onForntPic(String path) {
        frontPath = path;
        mDependView.onShowFrontPic(frontPath);
    }

    @Override
    public void onBackPic(String path) {
        backPath = path;
        mDependView.onShowBackPic(backPath);
    }

    @Override
    public void onAuth(final LoginEntity loginEntity) {

        if(TextUtil.isEmpty(frontPath)){
            mDependView.showMsg("请上传手持证件正面照");
            return;
        }

        if(TextUtil.isEmpty(backPath)){
            mDependView.showMsg("请上传证件正面照");
            return;
        }

        Observable<UploadFileEntity> frontObser = mNetDataManager.
                uploadFile(frontPath, AppConfig.FileSource.IDCARD);

        Observable<UploadFileEntity> backObser = mNetDataManager.
                uploadFile(backPath, AppConfig.FileSource.IDCARD);

        final List<UploadFileEntity> uploadFiles = new ArrayList<>();

        Observable.merge(frontObser,backObser)
                .subscribe(new DefaultNetSubscriber<UploadFileEntity>(mDialog) {

                    @Override
                    public void onNext(UploadFileEntity uploadFileEntity) {
                        super.onNext(uploadFileEntity);
                        uploadFiles.add(uploadFileEntity);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();

                        if(uploadFiles.size() == 2){

                            mNetDataManager.user_appeal(loginEntity.getIdNumber(),loginEntity.getName(),
                                    uploadFiles.get(0).getUrl(),uploadFiles.get(1).getUrl())
                                    .subscribe(new DefaultNetSubscriber<LoginEntity>(mDialog) {
                                        @Override
                                        public void onCompleted(LoginEntity loginEntity) {
                                            mDependView.onBusinessFinish(null);
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onCompleted(UploadFileEntity uploadFileEntity) {

                    }
                });

    }
}
