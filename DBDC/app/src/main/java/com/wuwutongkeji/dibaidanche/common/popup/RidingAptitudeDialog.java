package com.wuwutongkeji.dibaidanche.common.popup;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.base.BaseFragment;
import com.wuwutongkeji.dibaidanche.bike.BikeMaintainActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.rx.RxUtils;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import zxing.camera.CameraManager;

/**
 * Created by Mr.Bai on 17/9/20.
 *
 *
 * 智能锁
 */

public class RidingAptitudeDialog extends BaseFragment {

    public static final int CODE_PERMISSION = 10;


    final long USE_MONEY_TIMEUNIT = 1000 * 60 * 30; // 三十分钟

    @BindView(R.id.btn_flight)
    ImageView btnFlight;
    @BindView(R.id.btn_current)
    ImageView btnCurrent;
    @BindView(R.id.tv_bikeNum)
    TextView tvBikeNum;
    @BindView(R.id.btn_maintain)
    TextView btnMaintain;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    LockEntity mLockData;

    long time;

    Subscription mSubscription;

    boolean hasPermission;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_riding_aptitude;
    }

    @Override
    protected void initToolbar(View ChildView, Bundle savedInstanceState) {

    }

    @Override
    public void initViews(View childViews,Bundle savedInstanceState) {

        PermissionGen.with(RidingAptitudeDialog.this)
                .addRequestCode(CODE_PERMISSION)
                .permissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request();

        getView().setBackgroundColor(getResources().getColor(android.R.color.transparent));

        btnFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hasPermission){
                    CameraManager.init();
                    try {
                        CameraManager.get().openDriver(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CameraManager mCameraManager = CameraManager.get();
                    mCameraManager.flashHandler();

                    btnFlight.setSelected(mCameraManager.isFlight());
                }

            }
        });
        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != onRidingAptitudeListener){
                    onRidingAptitudeListener.onMoveCurrentMapLocation();
                }
            }
        });

        btnMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBikeMaintainActivity(mContext);
                intent.putExtra(BikeMaintainActivity.KEY_DATA,mLockData.getBicycleNum());
                startActivity(intent);
            }
        });

        tvBikeNum.setText(mLockData.getBicycleNum());

    }

    @Override
    public void initData() {

    }

    @PermissionFail(requestCode = CODE_PERMISSION)
    public void doFailSomething() {
        hasPermission = false;
    }

    //权限申请成功
    @PermissionSuccess(requestCode = CODE_PERMISSION)
    public void doSomething() {
        hasPermission = true;
        CameraManager.init();
        try {
            CameraManager.get().openDriver(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setLockData(LockEntity mLockData){
        this.mLockData = mLockData;
    }

    public LockEntity getLockData(){
        return mLockData;
    }

    @Override
    public void onResume() {
        super.onResume();

        long createTime = DateUtil.String2Long(
                mLockData.getCreateTime(),DateUtil.yyyy_MM_dd_HH__mm__ss);

        time = System.currentTimeMillis() - createTime;

        mSubscription = Observable.interval(0,1, TimeUnit.SECONDS)
                .compose(RxUtils.<Long>applyIOToMainThreadSchedulers())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        time += 1000;
                        tvTime.setText(DateUtil.formatLongToTimeStr(time));
                        boolean hasFreeCard = mLockData.isHasFreeCard() && time < USE_MONEY_TIMEUNIT * 4;
                        if(hasFreeCard){
                            tvMoney.setText("0元");
                        }else{
                            long ridingTime = (time  -  (hasFreeCard ? USE_MONEY_TIMEUNIT * 4 : 0) )
                                    / USE_MONEY_TIMEUNIT + 1;
                            tvMoney.setText(TextUtil.getMoneyByPenny(ridingTime
                                    * mLockData.getThirtyMinPrice()) + "元");
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        mSubscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(hasPermission){
            CameraManager mCameraManager = CameraManager.get();
            if(mCameraManager.isFlight()){
                mCameraManager.flashHandler();
            }
        }

        if (mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    private RidingAptitudeListener onRidingAptitudeListener;
    public void setOnRidingAptitudeListener(RidingAptitudeListener onRidingAptitudeListener){
        this.onRidingAptitudeListener = onRidingAptitudeListener;
    }
    public interface RidingAptitudeListener{
        void onMoveCurrentMapLocation();
    }
}
