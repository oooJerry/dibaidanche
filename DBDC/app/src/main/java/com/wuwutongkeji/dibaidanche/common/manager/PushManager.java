package com.wuwutongkeji.dibaidanche.common.manager;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.ta.utdid2.device.Device;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.net.NetDataManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.os.Looper.getMainLooper;

/**
 * Created by Mr.Bai on 17/9/17.
 */

public class PushManager {

    final String TAG = getClass().getSimpleName();

    private String deviceToken;

    private static PushManager instance;

    private PushManager(){}

    public static PushManager getInstance(){
        if(null == instance ){
            instance = new PushManager();
        }
        return instance;
    }

    public void init(Application application){

        PushAgent mPushAgent = PushAgent.getInstance(application);
        mPushAgent.setDebugMode(false);
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(final String token) {
                String Token = SharedPreferencesUtil.read(AppConfig.DEVICE_TOKEN);

                if((TextUtil.isEmpty(Token) ||
                        !(token.equals(Token))) &&
                        !TextUtil.isEmpty(SharedPreferencesUtil.read(AppConfig.USERKEYS.LOGINTOKEN))){
                    NetDataManager.getInstance().user_uploadDeviceToken(token)
                            .subscribe(new DefaultNetSubscriber<Void>(null) {
                                @Override
                                public void onCompleted(Void aVoid) {
                                    SharedPreferencesUtil.write(AppConfig.DEVICE_TOKEN,token);
                                }
                            });
                }
                deviceToken = token;
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.d(s + " - " + s1);
            }
        });

        mPushAgent.setMessageHandler(new UmengMessageHandler(){
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                Log.d(TAG,msg.custom);
                try {
                    JSONObject object = new JSONObject(msg.custom);
                    AppConfig.LockType type = AppConfig.LockType.valueOfCode(object.optInt("code"));
                    EventBus.getDefault().post(type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getDeviceToken(){
        return deviceToken;
    }

}
