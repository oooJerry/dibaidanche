package com.wuwutongkeji.dibaidanche.common.net;


import com.orhanobut.logger.Logger;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.util.HttpUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Mr.Bai on 2017/9/11.
 */
public class NetInterceptor implements Interceptor {

    String TAG = getClass().getSimpleName();

    private OkHttpClient okHttpClient;

    public void setOkHttpClient(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request;

        String loginToken = SharedPreferencesUtil.read(AppConfig.USERKEYS.LOGINTOKEN);
        System.out.println("获取loginToken:"+loginToken);
        if(!TextUtil.isEmpty(loginToken)){

            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("loginToken", loginToken)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            request = requestBuilder.build();

        }else{
            request = chain.request();
        }

        Response mInterceptResponse = null;
        try {
            mInterceptResponse = chain.proceed(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        String json = HttpUtil.getBody(mInterceptResponse);

        Logger.d(json);

        return mInterceptResponse;
    }
}
