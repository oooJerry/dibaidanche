package com.wuwutongkeji.dibaidanche.common.net;


import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.util.GsonUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Mr.Bai on 2017/9/7.
 */
public class NetWorkManager {

    private static NetWorkManager Instance;
    private NetService mNetService;


    public static NetWorkManager getInstance() {
        if (Instance == null) {
            Instance = new NetWorkManager();
        }
        return Instance;
    }
    private NetWorkManager(){

        NetInterceptor mNetInterceptor = new NetInterceptor();
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(mNetInterceptor)
                .addInterceptor(mLoggingInterceptor)
                .build();

        mNetInterceptor.setOkHttpClient(okHttpClient);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppInterface.ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mNetService = retrofit.create(NetService.class);
    }

    public NetService getNetService(){
        return mNetService;
    }

    public NetDataManager getNetDataManager(){
        return NetDataManager.getInstance();
    }
}
