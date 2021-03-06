package com.wuwutongkeji.dibaidanche.bike.contract.maintain;

import android.net.Uri;
import android.text.Html;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.manager.LocationManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.OptionTypeEntity;
import com.wuwutongkeji.dibaidanche.entity.UploadFileEntity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Mr.Bai on 17/10/2.
 */

public class BikeMaintainPresenter extends BikeMaintainContract.BikeMaintainBasePresenter {

    @Override
    protected void onAttach() {
        super.onAttach();

        mNetDataManager.repairs_types()
                .subscribe(new DefaultNetSubscriber<List<OptionTypeEntity>>(mDialog) {
                    @Override
                    public void onCompleted(List<OptionTypeEntity> optionTypeEntities) {
                        mDependView.onLoadOption(optionTypeEntities);
                    }
                });
    }

    @Override
    public void onSubmit(final String bicycleNum, final int feedbackType, final String feedbackContent, List<Uri> imgs) {

        if(TextUtil.isEmpty(bicycleNum)){
            mDependView.showMsg("请输入车辆编号");
            return;
        }
        if(0 == feedbackType){
            mDependView.showMsg("请选择报修类型");
            return;
        }
        if(feedbackContent.length() > 200){
            mDependView.showMsg("请输入200个字符之内");
            return;
        }
        final List<String> imgUrls = new ArrayList<>();
        if(!imgs.isEmpty()){

            Observable.from(imgs)
                    .flatMap(new Func1<Uri, Observable<UploadFileEntity>>() {
                        @Override
                        public Observable<UploadFileEntity> call(Uri uri) {
                            return mNetDataManager.uploadFile(uri.getPath(), AppConfig.FileSource.FEEDBACK);
                        }
                    })
                    .subscribe(new DefaultNetSubscriber<UploadFileEntity>(mDialog) {
                        @Override
                        public void onCompleted(UploadFileEntity uploadFileEntity) {

                        }

                        @Override
                        public void onNext(UploadFileEntity uploadFileEntity) {
                            super.onNext(uploadFileEntity);
                            imgUrls.add(uploadFileEntity.getUrl());
                        }

                        @Override
                        public void onCompleted() {
                            super.onCompleted();
                            submit(bicycleNum,feedbackType,feedbackContent,imgUrls);
                        }
                    });

        }else{
            submit(bicycleNum,feedbackType,feedbackContent,imgUrls);
        }
    }

    private void submit(String bicycleNum, int feedbackType, String feedbackContent, List<String> imgs){

        MultipartBody.Builder mFormBuilder = new MultipartBody.Builder();
        mFormBuilder.addFormDataPart("userId", SharedPreferencesUtil.getUser().getId());
        mFormBuilder.addFormDataPart("bicycleNum",bicycleNum);
        mFormBuilder.addFormDataPart("repairsType",String.valueOf(feedbackType));
        mFormBuilder.addFormDataPart("repairsContent",feedbackContent);
        mFormBuilder.addFormDataPart("repairsLon", LocationManager.getLongitude());
        mFormBuilder.addFormDataPart("repairsLat",LocationManager.getLatitude());
        mFormBuilder.addFormDataPart("repairsTime",String.valueOf(System.currentTimeMillis()));
        for(String s: imgs){
            mFormBuilder.addFormDataPart("repairsImg",s);
        }

        mNetDataManager.repairs_add(mFormBuilder.build())
                .subscribe(new DefaultNetSubscriber<Void>(mDialog) {
                    @Override
                    public void onCompleted(Void aVoid) {
                        mDependView.onBusinessFinish(null);
                    }
                });
    }
}
