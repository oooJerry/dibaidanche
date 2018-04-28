package com.wuwutongkeji.dibaidanche.launch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.bike.BikeUsingFinishActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.CloseLockEntity;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.LogoutEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositQueryEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositRefundEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletRechargeEntity;
import com.wuwutongkeji.dibaidanche.launch.contract.main.MainContract;
import com.wuwutongkeji.dibaidanche.launch.contract.main.MainPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mr.Bai on 2017/9/7.
 */

public class MainActivity extends BaseToolbarActivity
        implements MainContract.MainBaseView,SensorEventListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.icon_header)
    SimpleDraweeView iconHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_creditScore)
    TextView tvCreditScore;
    @BindView(R.id.mapView)
    MapView mMapView;
    AMap aMap;

    @BindView(R.id.btn_scanCode)
    LinearLayout btnScanCode;
    @BindView(R.id.icon_current)
    ImageView iconCurrent;
    @BindView(R.id.icon_update)
    ImageView iconUpdate;

    SensorManager mSensorManager;
    MainContract.MainBasePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        iconClose.setImageResource(R.mipmap.icon_main_menu);
        Drawable mainTitleDrawable = mContext.getResources().getDrawable(R.mipmap.icon_main_title);
        mainTitleDrawable.setBounds(0, 0, mainTitleDrawable.getMinimumWidth(), mainTitleDrawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(mainTitleDrawable, null, null, null);

        mMapView.onCreate(savedInstanceState);

        aMap = mMapView.getMap();
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setLogoBottomMargin(-50);

        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = mPresenter.getIntentByUserState(mContext);
                if(null != intent){
                    if(!( mPresenter.getUserState() == AppConfig.UserState.NO_PAY)){
                        startActivity(intent);
                        return;
                    }
                }
                if((!mPresenter.isHaveBalance() || !mPresenter.isPayBalance()) && !mPresenter.checkHavfreeCards() ){
//                    showMsg("您的余额不足,请充值");
                    startActivity(AppIntent.getWalletRechargeActivity(mContext));
                }else{
                    startActivity(AppIntent.getScanCodeUnlockActivity(mContext));
                }
            }
        });

        iconCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onCenter2Current();
                mPresenter.onMoveMapLocation();
                mPresenter.onCenterMarkerAnima();
            }
        });

        iconUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onRefreshRotaAnima(view);
                mPresenter.onQueryBicycleByGisOfFree();
            }
        });
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new MainPresenter(aMap,mContext), this);
        aMap.setOnCameraChangeListener(mPresenter);
        aMap.setInfoWindowAdapter(mPresenter);

        if(TextUtil.isEmpty(SharedPreferencesUtil.getUser().getLoginToken())){
            startActivity(AppIntent.getLoginActivity(mContext));
        }

    }


    @Override
    public void onBackPressed() {
        if(mPresenter.getUserState() == AppConfig.UserState.NO_LOGIN){
            startActivity(AppIntent.getLoginActivity(mContext));
            return;
        }
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.icon_main_menu_feedback) {
            startActivity(AppIntent.getHelpActivity(mContext));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.icon_feedback,R.id.icon_header,R.id.nav_journey, R.id.nav_wallet,  R.id.nav_credit,
            R.id.nav_inviteFriend, R.id.nav_help, R.id.nav_aboutUs})
    public void onNavClick(View view) {
        int id = view.getId();

        if (id == R.id.nav_help) { // 使用指南
            startActivity(AppIntent.getHelpActivity(mContext));
        }
        if(id == R.id.icon_feedback){ // 反馈
            startActivity(AppIntent.getBikeFeedbackActivity(mContext));
        }
        if (id == R.id.icon_header) { // 个人信用更改
            startActivity(AppIntent.getPersonalInfoActivity(mContext));
        }
        if (id == R.id.nav_wallet) { // 我的钱包
            startActivity(AppIntent.getWalletActivity(mContext));
        }
        if (id == R.id.nav_journey) { // 我的行程
            startActivity(AppIntent.getJourneyActivity(mContext));
        }
        if (id == R.id.nav_credit) { // 我的信用
            startActivity(AppIntent.getCreditActivity(mContext));
        }
        if (id == R.id.nav_inviteFriend) { // 邀请好友
            startActivity(AppIntent.getInviteFriendActivity(mContext));
        }
        if (id == R.id.nav_aboutUs) { // 关于我们
            startActivity(AppIntent.getAboutUsActivity(mContext));
        }
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        aMap.setOnCameraChangeListener(null);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        mPresenter.checkHavfreeCard();
        mPresenter.checkUserStateDialog(mContext);
        super.onResume();
    }

    @Override
    protected void onStart() {
        startSensor();
        super.onStart();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        stopSensor();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public MainContract.MainBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }

    @Override
    public Marker onAddMarker(MarkerOptions markerOptions) {
        return aMap.addMarker(markerOptions);
    }

    @Override
    public void onMoveMapLocation(CameraUpdate cameraUpdate) {
        aMap.animateCamera(cameraUpdate);
    }

    @Override
    public void showDialog(Fragment mDialog) {

        if(null == mDialog){
            return;
        }


        if (mDialog instanceof BaseDialog) {

            BaseDialog baseDialog = (BaseDialog) mDialog;

            Dialog dialog = baseDialog.getDialog();

            if (null != dialog && dialog.isShowing()) {
                return;
            }
            baseDialog.show(getSupportFragmentManager());
        } else {

            if (mDialog.isAdded()) {
                return;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, mDialog)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void dismissDialog(Fragment mDialog) {

        if(null == mDialog){
            return;
        }

        if (mDialog instanceof BaseDialog) {

            BaseDialog baseDialog = (BaseDialog) mDialog;

            Dialog dialog = baseDialog.getDialog();

            if (null == dialog || !dialog.isShowing()) {
                return;
            }
            baseDialog.dismiss();
        } else {

            if (!mDialog.isAdded()) {
                return;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mDialog)
                    .commitAllowingStateLoss();
        }
    }

    // 更新侧滑显示
    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onUpdatePersonalInfo(LoginEntity entity) {
        if(null != entity){
            tvName.setText(entity.getNickname());
            tvCreditScore.setText((entity.isAuthId() ? "已认证" : "未认证" ) + ":信用分" + entity.getCredit());
            iconHeader.setImageURI(entity.getPhotoUrl());
        }else {
            tvName.setText(null);
            tvCreditScore.setText(null);
            iconHeader.setImageURI("");
        }
    }

    // 退出登录
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutEntity entity){
        mPresenter.onLogout(mContext);
    }

    // 获取锁类型
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void queryRidingLockType(WalletDepositQueryEntity queryEntity){
        mPresenter.queryRidingLockType(queryEntity);
    }

    // 退押金
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDepositRefund(WalletDepositRefundEntity entity){
        mPresenter.onDepositRefund(this);
    }

    // 充值
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWalletRecharge(WalletRechargeEntity entity){
        mPresenter.onWalletRecharge(entity.blance);
    }

    // 开锁
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenLock(LockEntity entity){
        mPresenter.onOpenLock(entity);
    }

    // 开锁 或关锁
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAptitudeLockOption(AppConfig.LockType type){
        mPresenter.onAptitudeLockOption(type);
    }

    // 关锁
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseLock(CloseLockEntity entity){
        mPresenter.onCloseLock();
        Intent intent = AppIntent.getBikeUsingFinishActivity(mContext);
        intent.putExtra(BikeUsingFinishActivity.KEY_DATA,entity.getConsumeId());
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPresenter.closeDrawer(drawer)) {
                return true;
            }
            mPresenter.exitApp();
        }
        return true;
    }



    public void startSensor(){
        Sensor mSensor = null;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager!=null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if(mSensor!=null){
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
    public void stopSensor(){
        if(null != mSensorManager){
            mSensorManager.unregisterListener(this);
        }
    }

    float mSensorLastX;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            float x = event.values[SensorManager.DATA_X];
            if(Math.abs(x - mSensorLastX)>1.0){
                mPresenter.onMarkerRotate(x);
            }
            mSensorLastX = x;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
