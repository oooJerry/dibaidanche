package com.wuwutongkeji.dibaidanche.common.popup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.base.BaseFragment;
import com.wuwutongkeji.dibaidanche.bike.BikeMaintainActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.net.NetDataManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.rx.RxUtils;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.CloseLockEntity;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import zxing.camera.CameraManager;

/**
 * Created by Mr.Bai on 17/9/20.
 * <p>
 * <p>
 * 机械锁
 */

public class RidingMachineDialog extends BaseFragment {


    public static final int CODE_PERMISSION = 10;


    final long USE_MONEY_TIMEUNIT = 1000 * 60 * 30; // 三十分钟

    @BindView(R.id.btn_flight)
    ImageView btnFlight;
    @BindView(R.id.btn_current)
    ImageView btnCurrent;
    @BindView(R.id.tv_bikeNum)
    TextView tvBikeNum;
    @BindView(R.id.tv_bikePwd)
    TextView tvBikePwd;
    @BindView(R.id.btn_maintain)
    TextView btnMaintain;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.btn_finish)
    Button btnFinish;

    LockEntity mLockData;

    long time;

    Subscription mSubscription;

    UnLockPwdDialog mUnLockPwdDialog;

    boolean hasPermission;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_riding_machine;
    }

    @Override
    public void initViews(View ChildView, Bundle savedInstanceState) {

        PermissionGen.with(RidingMachineDialog.this)
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
                if(null != onRidingMachineListener){
                    onRidingMachineListener.onMoveCurrentMapLocation();
                }
            }
        });
        btnMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBikeMaintainActivity(mContext);
                intent.putExtra(BikeMaintainActivity.KEY_DATA, mLockData.getBicycleNum());
                startActivity(intent);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBikeUsingFinishActivity(mContext);
                startActivity(intent);
            }
        });

        tvBikePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUnLockPwdDialog.show(getFragmentManager());
            }
        });

        tvBikeNum.setText(mLockData.getBicycleNum());
        tvBikePwd.setText(mLockData.getPassword());
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCloseLock();
            }
        });

    }

    @Override
    public void initData() {
        mUnLockPwdDialog = new UnLockPwdDialog();
        mUnLockPwdDialog.setPwd(mLockData.getPassword());
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

    @Override
    public void onResume() {
        super.onResume();

        long createTime = DateUtil.String2Long(
                mLockData.getCreateTime(), DateUtil.yyyy_MM_dd_HH__mm__ss);

        time = System.currentTimeMillis() - createTime;

        mSubscription = Observable.interval(0, 1, TimeUnit.SECONDS)
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

    @Override
    protected void initToolbar(View ChildView, Bundle savedInstanceState) {

    }

    public void setLockData(LockEntity mLockEntity) {
        this.mLockData = mLockEntity;
    }

    public void onCloseLock() {
        NetDataManager.getInstance().bicycle_closeLock(
                mLockData.getUserId(), mLockData.getLockNum(),
                mLockData.getBicycleNum(), mLockData.getConsumeId())
                .subscribe(new DefaultNetSubscriber<Void>(mLoadingDialog) {
                    @Override
                    public void onCompleted(Void aVoid) {
                        CloseLockEntity entity = new CloseLockEntity();
                        entity.setConsumeId(mLockData.getConsumeId());
                        EventBus.getDefault().post(entity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showMsg(e.getMessage());
                    }
                });
    }

    private RidingMachineListener onRidingMachineListener;
    public void setOnRidingMachineListener(RidingMachineListener onRidingMachineListener){
        this.onRidingMachineListener = onRidingMachineListener;
    }
    public interface RidingMachineListener{
        void onMoveCurrentMapLocation();
    }
}
