package com.wuwutongkeji.dibaidanche.common.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.net.NetDataManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.UploadEntity;


/**
 * Created by Mr.Bai on 17/10/5.
 */

public class UploadManager {

    public static void checkUpdate(final Context mContext){

        NetDataManager.getInstance()
                .version_last()
                .subscribe(new DefaultNetSubscriber<UploadEntity>(null) {
                    @Override
                    public void onCompleted(UploadEntity uploadEntity) {

                        if(null == uploadEntity){
                            return;
                        }
                        if(uploadEntity.isMustUpdate()){
                            onShowMsgDialog(mContext,false,uploadEntity);
                        }else{

                            if(isUpdate(uploadEntity.getDependOn())){

                                onShowMsgDialog(mContext,false,uploadEntity);

                            }else if(isUpdate(uploadEntity.getVersion())){

                                onShowMsgDialog(mContext,true,uploadEntity);

                            }
                        }
                    }
                });
    }


    private static void onShowMsgDialog(Context mContext,boolean cancelable,UploadEntity entity){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("发现新版本")
                .setMessage(entity.getDesc())
                .setCancelable(cancelable)
                .setPositiveButton("确定",new PositiveButtonClicklistener(mContext,entity));

        if(cancelable){
            builder.setNegativeButton("取消",new NegativeButtonClicklistener(entity));
        }

        builder.show();
    }

    private static boolean isUpdate(String newVersion){

        String version = DeviceUtil.getVersionName();

        if(TextUtil.isEmpty(version)){
            return true;
        }
        if(TextUtil.isEmpty(newVersion)){
            return false;
        }

        String[] newVersions = newVersion.split("\\.");
        String[] versions = version.split("\\.");

        int newVersionLength = newVersions.length;
        int versonsLength = versions.length;

        int lengh =  newVersionLength > versonsLength ? newVersionLength : versonsLength;

        for(int i = 0 ; i < lengh; i ++){

            int newversionCode = 0;
            int versionCode = 0;

            try {
                newversionCode = Integer.valueOf(newVersions[i]);
            }catch (IndexOutOfBoundsException e){
                return false;
            }

            try {
                versionCode = Integer.valueOf(versions[i]);
            }catch (IndexOutOfBoundsException e){
                return true;
            }

            if(newversionCode > versionCode){
                return true;
            }
        }
        return false;
    }


    static class PositiveButtonClicklistener implements DialogInterface.OnClickListener {

        UploadEntity entity;
        Context mContext;

        public PositiveButtonClicklistener(Context mContext,UploadEntity entity){
            this.entity = entity;
            this.mContext = mContext;
        }
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            Intent intent = AppIntent.getSystemBrowserActivity(entity.getDownloadUrl());
            mContext.startActivity(intent);
        }
    }

    static class NegativeButtonClicklistener implements DialogInterface.OnClickListener{

        UploadEntity entity;

        public NegativeButtonClicklistener(UploadEntity entity){
            this.entity = entity;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }
}
