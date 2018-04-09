package com.wuwutongkeji.dibaidanche;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.wuwutongkeji.dibaidanche.bike.service.RidingAptitudeService;
import com.wuwutongkeji.dibaidanche.common.manager.LocationManager;
import com.wuwutongkeji.dibaidanche.common.manager.PushManager;

/**
 * Created by Mr.Bai on 2017/9/7.
 */

public class LaunchApp extends Application {

    public static LaunchApp Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        initLocation();
        initLogger();
        initFresco();
        initPush();
        initBugly();
        initLeakCanary();
        initRidingAptitudeService();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initLocation(){
        LocationManager.initialize();
    }
    private void initLogger(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    private void initFresco(){
        Fresco.initialize(this);
    }
    private void initPush(){
        PushManager.getInstance().init(this);
    }
    private void initBugly(){
        CrashReport.initCrashReport(this, "5d99e02f32", false);
    }
    private void initRidingAptitudeService(){
        mRidingAptitudeService = RidingAptitudeService.init();
    }
    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        LaunchApp application = (LaunchApp) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;
    private static RidingAptitudeService mRidingAptitudeService;
}
