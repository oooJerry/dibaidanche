package com.wuwutongkeji.dibaidanche.launch.contract.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.orhanobut.logger.Logger;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.manager.LocationManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPublicityDialog;
import com.wuwutongkeji.dibaidanche.common.popup.RidingAptitudeDialog;
import com.wuwutongkeji.dibaidanche.common.popup.RidingMachineDialog;
import com.wuwutongkeji.dibaidanche.common.popup.UnLockLoadingDialog;
import com.wuwutongkeji.dibaidanche.common.popup.UnLockPwdDialog;
import com.wuwutongkeji.dibaidanche.common.popup.UserStateFragment;
import com.wuwutongkeji.dibaidanche.common.route.WalkRouteOverlay;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.CloseLockEntity;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;
import com.wuwutongkeji.dibaidanche.entity.LockByGisEntity;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.RidingAptitudeOptionEntity;
import com.wuwutongkeji.dibaidanche.entity.UserInfoEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositQueryEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Mr.Bai on 2017/9/13.
 */

public class MainPresenter extends MainContract.MainBasePresenter {

    final String KEY_MARKER_TYPE = "KEY_MARKER_TYPE";
    final String KEY_MARKER_DATA = "KEY_MARKER_DATA";
    final String KEY_MARKER_DATA_OPT = "KEY_MARKER_DATA_OPT";
    final int TYPE_MARKER_BIKE = 1;

    Marker mCenterPositionMarker;
    Marker mCurrentPositionMarker;

    AppConfig.LockType mAptitudeType;

    UserStateFragment mUserStateFragment; // 用户状态弹框
    UnLockPwdDialog mUnLockPwdDialog; // 机械锁密码弹框
    UnLockLoadingDialog mUnLockLoadingDialog; // 智能锁开锁加载
    RidingAptitudeDialog mRidingAptitudeDialog; // 智能锁
    RidingMachineDialog mRidingMachineDialog; // 机械锁

    AppConfig.UserState mUserState = AppConfig.UserState.NO_LOGIN;

    List<Marker> mBicycleList = new ArrayList<>();

    AMap aMap;

    Context mContext;

    boolean HavfreeCard = false;

    WalkRouteOverlay walkRouteOverlay;

    public MainPresenter(AMap aMap,Context mContext){
        this.aMap = aMap;
        this.mContext = mContext;
        walkRouteOverlay = new WalkRouteOverlay(mContext,aMap);

        final RouteSearch routeSearch = new RouteSearch(mContext);
        routeSearch.setRouteSearchListener(this);

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                removeWalkLine();
            }
        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Integer markerType = getMarkerType(marker);
                if(null == markerType){
                    return false;
                }

                if(null == mCenterPositionMarker){
                    return false;
                }
                double fromLat = mCenterPositionMarker.getPosition().latitude;
                double fromLng = mCenterPositionMarker.getPosition().longitude;

                mDialog.show();

                RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                        new LatLonPoint(fromLat,fromLng ),
                        new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude));
                RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WALK_DEFAULT);
                routeSearch.calculateWalkRouteAsyn(query);
                walkRouteOverlay.setSelectedMarker(marker);
                return true;
            }
        });


    }

    @Override
    protected void onAttach() {
        super.onAttach();

        mUserStateFragment = new UserStateFragment();
        mUnLockPwdDialog = new UnLockPwdDialog();
        mUnLockLoadingDialog = new UnLockLoadingDialog();
        mRidingAptitudeDialog = new RidingAptitudeDialog();
        mRidingMachineDialog = new RidingMachineDialog();

        mRidingMachineDialog.setOnRidingMachineListener(new RidingMachineDialog.RidingMachineListener() {
            @Override
            public void onMoveCurrentMapLocation() {
                MainPresenter.this.onMoveMapLocation();
            }
        });

        mRidingAptitudeDialog.setOnRidingAptitudeListener(new RidingAptitudeDialog.RidingAptitudeListener() {
            @Override
            public void onMoveCurrentMapLocation() {
                MainPresenter.this.onMoveMapLocation();
            }
        });

        final LocationManager mLocationManager = LocationManager.initialize();
        mLocationManager.addLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                LatLng latLng = new LatLng(aMapLocation.getLatitude(),
                        aMapLocation.getLongitude());

                if(null == mCenterPositionMarker){
                    mCenterPositionMarker = mDependView.onAddMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(
                                    R.mipmap.icon_launch_center_position)));
                    mCenterPositionMarker.setZIndex(1);
                    onMoveMapLocation();
                }

                if(null == mCurrentPositionMarker){
                    mCurrentPositionMarker = mDependView.onAddMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(
                                    R.mipmap.icon_current_location)));

                }

                mCurrentPositionMarker.setPosition(latLng);

            }
        });
    }

    @Override
    public void onCreate(Bundle outState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void checkUserStateDialog(final Context mContext) {

        if(null == mUserState){
            return;
        }
        mNetDataManager.user_info()
                .subscribe(new DefaultNetSubscriber<NetModel<UserInfoEntity>>(mDialog) {
                    @Override
                    public void onCompleted(NetModel<UserInfoEntity> userInfoEntityNetModel) {

                        if(null == userInfoEntityNetModel ||
                                AppConfig.NO_LOGIN.equals(userInfoEntityNetModel.getReturnCode())){
                            mUserState = AppConfig.UserState.NO_LOGIN;
                            Intent intent = AppIntent.getLoginActivity(mContext);
                            mUserStateFragment.setData("您尚未完成手机验证，请先进行手机验证"
                                    , intent);
                            mDependView.showDialog(mUserStateFragment);
                            return;
                        }

                        UserInfoEntity userInfoEntity = userInfoEntityNetModel.getData();

                        if(null == userInfoEntity){
                            mDependView.showMsg(userInfoEntityNetModel.getDesc());
                            return;
                        }
                        HavfreeCard = userInfoEntity.isHasFreeCard();
                        LoginEntity loginEntity = userInfoEntity.getUser();

                        if(null == loginEntity){
                            mDependView.showMsg(AppConfig.NET_ERROR);
                            return;
                        }
                        LoginEntity entity = SharedPreferencesUtil.getUser();
                        loginEntity.setLoginToken(entity.getLoginToken());
                        SharedPreferencesUtil.saveUser(loginEntity);
                        EventBus.getDefault().post(loginEntity);

                        LockEntity lockEntity = userInfoEntity.getConsumeRecord();
                        if(null != lockEntity ){
                            lockEntity.setHasFreeCard(HavfreeCard);
                            lockEntity.setConsumeId(lockEntity.getId());
                            EventBus.getDefault().post(lockEntity);
                            return;
                        }

                        if(!loginEntity.isPayDeposit()){
                            mUserState = AppConfig.UserState.NO_PAY;
                            if(!HavfreeCard){
                                Intent intent = AppIntent.getDepositActivity(mContext);
                                SharedPreferencesUtil.saveUser(entity);
                                mUserStateFragment.setData("您尚未充押金，无法租车骑行"
                                        , intent);
                                mDependView.showDialog(mUserStateFragment);
                            }
                            return;
                        }

                        if(!loginEntity.isAuthId()){
                            mUserState = AppConfig.UserState.NO_AUTH;
                            Intent intent = AppIntent.getApproveActivity(mContext);
                            mUserStateFragment.setData("您尚未实名认证，无法租车骑行"
                                    , intent);
                            mDependView.showDialog(mUserStateFragment);
                            return;
                        }
                        mUserState = null;
                        mDependView.dismissDialog(mUserStateFragment);

                    }
                });
    }

    @Override
    public void checkHavfreeCard() {
        mNetDataManager.freeCard_list_all("0")
                .subscribe(new DefaultNetSubscriber<NetModel<List<FreeCardEntity>>>(mDialog) {
                    @Override
                    public void onCompleted(NetModel<List<FreeCardEntity>> netModel) {

                        if("NO_LOGIN".equals(netModel.getReturnCode())){
                            return;
                        }
                        long time = SharedPreferencesUtil.readLong(AppConfig.SHOWCARD_TIME,0);

                        long todayTime = DateUtil.getTodayStartDate();

                        boolean havfreeCard = true;
                        if(todayTime != time){

                            if(null != netModel.getData()){
                                for(FreeCardEntity freeCardEntity: netModel.getData()){
                                    if(freeCardEntity.getRemainTime() > 0){
                                        havfreeCard = false;
                                        break;
                                    }
                                }
                            }
                            if(havfreeCard){
                                SharedPreferencesUtil.writeLong(AppConfig.SHOWCARD_TIME,todayTime);
                                FreeCardPublicityDialog freeCardPublicityDialog
                                        = new FreeCardPublicityDialog();
                                mDependView.showDialog(freeCardPublicityDialog);
                            }
                        }
                    }
                });
    }

    @Override
    public boolean checkHavfreeCards() {
        return HavfreeCard;
    }

    @Override
    public Intent getIntentByUserState(Context mContext) {
        if(mUserState == AppConfig.UserState.NO_LOGIN){
            return AppIntent.getLoginActivity(mContext);
        }
        if(mUserState == AppConfig.UserState.NO_PAY){
            return AppIntent.getDepositActivity(mContext);
        }
        if(mUserState == AppConfig.UserState.NO_AUTH){
            return AppIntent.getApproveActivity(mContext);
        }
        return null;
    }

    @Override
    public AppConfig.UserState getUserState() {
        return mUserState;
    }

    @Override
    public void onUpdatePersonalInfo(LoginEntity entity) {
        mDependView.onUpdatePersonalInfo(entity);
    }

    @Override
    public boolean closeDrawer(DrawerLayout drawer) {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
            return true;
        }
        return false;
    }

    long clickTime;

    @Override
    public boolean exitApp() {
        if (SystemClock.uptimeMillis() - clickTime <= 1500) {
            System.exit(0);
        } else {
            clickTime = SystemClock.uptimeMillis();
            mDependView.showMsg("再按一次退出应用！");
        }
        return true;
    }

    @Override
    public void onMoveMapLocation() {
        if(null == mCenterPositionMarker || null == mCenterPositionMarker){
            return;
        }
        removeWalkLine();
        // 改变地图伸缩大小
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition(mCenterPositionMarker.getPosition(), 15, 0, 0));
        mDependView.onMoveMapLocation(update);
    }

    @Override
    public void onCenter2Current() {
        if(null == mCenterPositionMarker || null == mCenterPositionMarker){
            return;
        }
        mCenterPositionMarker.setPosition(mCurrentPositionMarker.getPosition());
    }

    @Override
    public void onQueryBicycleByGisOfFree() {

        removeWalkLine();
        LatLng latLng = mCenterPositionMarker.getPosition();

        String lng = String.valueOf(latLng.longitude);
        String lat = String.valueOf(latLng.latitude);

        String radius = String.valueOf(AppConfig.RADIUS_QUERYBICYCLEBYGIS);
        String bicycleUsingStatus = AppConfig.BikeUsingStatus.BICYCLE_FREE.getBicycleUsingStatus();

        mNetDataManager.bicycle_queryBicycleByGis(lng,lat,radius,bicycleUsingStatus)
                .flatMap(new Func1<NetModel<List<LockByGisEntity>>, Observable<Marker>>() {
                    @Override
                    public Observable<Marker> call(NetModel<List<LockByGisEntity>> listNetModel) {
                        if(null == listNetModel.getData()){
                            return null;
                        }

                        for(Marker marker: mBicycleList){
                            marker.remove();
                        }
                        mBicycleList.clear();
                        for(LockByGisEntity entity : listNetModel.getData()){
                            LatLng currentLatlng = new LatLng(entity.getLat(),entity.getLon());
                            Marker marker = mDependView.onAddMarker(new MarkerOptions()
                                    .position(currentLatlng)
                                    .icon(BitmapDescriptorFactory.fromResource(
                                            R.mipmap.icon_bicycle)).title("测试"));

                            Bundle bundle = new Bundle();
                            bundle.putInt(KEY_MARKER_TYPE,TYPE_MARKER_BIKE);
                            bundle.putSerializable(KEY_MARKER_DATA,entity);
                            marker.setObject(bundle);

                            mBicycleList.add(marker);
                        }
                        return Observable.from(mBicycleList);
                    }
                })
                .subscribe(new DefaultNetSubscriber<Marker>(null) {

                    @Override
                    public void onCompleted(Marker marker) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Marker marker) {
                        super.onNext(marker);
                        marker.setZIndex(-1);
                        mBicycleList.add(marker);
                    }
                });
    }

    @Override
    public void onLogout(Context mContext) {
        mUserState = AppConfig.UserState.NO_LOGIN;
        mUserStateFragment.setData("您尚未完成手机验证，请先进行手机验证"
                , AppIntent.getLoginActivity(mContext));
        mDependView.showDialog(mUserStateFragment);
        mDependView.onUpdatePersonalInfo(null);
    }

    @Override
    public void queryRidingLockType(WalletDepositQueryEntity queryEntity) {
        if(null != mAptitudeType){
            queryEntity.setLockType(mAptitudeType);
        }
    }

    @Override
    public void onDepositRefund(Context mContext) {
        mUserState = AppConfig.UserState.NO_PAY;

        LoginEntity entity = SharedPreferencesUtil.getUser();
        entity.setPayDeposit(false);
        SharedPreferencesUtil.saveUser(entity);
        mUserStateFragment.setData("您尚未充押金，无法租车骑行"
                , AppIntent.getDepositActivity(mContext));
        mDependView.showDialog(mUserStateFragment);
    }
    @Override
    public void onWalletRecharge(long balance) {
        LoginEntity entity = SharedPreferencesUtil.getUser();
        entity.setBalance(balance);
        entity.setPayBalance(true);
        SharedPreferencesUtil.saveUser(entity);
    }
    @Override
    public void onOpenLock(LockEntity entity) {

        if(null != mCenterPositionMarker){
            onMarkerVisible(mCenterPositionMarker,false);
        }
        for(Marker marker: mBicycleList){
            onMarkerVisible(marker,false);
        }

        if(isMachineLock(entity.getPassword())){
            mUnLockPwdDialog.setPwd(entity.getPassword());
            mRidingMachineDialog.setLockData(entity);
            mDependView.showDialog(mRidingMachineDialog);
            mDependView.showDialog(mUnLockPwdDialog);

        }else{

            mAptitudeType = null;

            mUnLockLoadingDialog.setLockData(entity);

            AppConfig.LockType lockType = AppConfig.LockType.valueOfCode(entity.getStatus());

//            EventBus.getDefault().post(lockType);  这样做会有bug eventbus 会报空指针

            onAptitudeLockOption(lockType);

        }
    }

    @Override
    public void onAptitudeLockOption(AppConfig.LockType type) {

        LockEntity lockEntity = mUnLockLoadingDialog.getLockData();

        if(mAptitudeType == type || null == lockEntity){
            return;
        }
        RidingAptitudeOptionEntity optionEntity = new RidingAptitudeOptionEntity();
        optionEntity.setLockType(type);
        optionEntity.setConsumeId(lockEntity.getConsumeId());

        mAptitudeType = type;

        if(type == AppConfig.LockType.LOCK_OPENING) { // 正在开锁

            // 后台轮询
            EventBus.getDefault().post(optionEntity);

            mDependView.showDialog(mUnLockLoadingDialog);

        }else if(type == AppConfig.LockType.LOCK_OPENED_SUCCESS){ // 开锁成功

            // 后台轮询
            EventBus.getDefault().post(optionEntity);

            mDependView.dismissDialog(mUnLockLoadingDialog);
            mRidingAptitudeDialog.setLockData(lockEntity);
            mDependView.showDialog(mRidingAptitudeDialog);

        }else if(type == AppConfig.LockType.TRAVEL_FINISHED){ // 骑行结束

            CloseLockEntity entity = new CloseLockEntity();
            entity.setConsumeId(lockEntity.getConsumeId());
            mRidingAptitudeDialog.setLockData(null);
            EventBus.getDefault().post(entity);

        }else if(type == AppConfig.LockType.LOCK_OPENED_FAILED){ // 开锁失败
            onCloseLock();
            mAptitudeType = type;
            mRidingAptitudeDialog.setLockData(null);
            mDependView.showMsg(type.getMsg());

        }

    }

    @Override
    public void onCloseLock() {
        if(null != mCenterPositionMarker) {
            onMarkerVisible(mCenterPositionMarker,true);
        }
        for(Marker marker: mBicycleList){
            onMarkerVisible(marker,true);
        }
        mDependView.dismissDialog(mUnLockLoadingDialog);
        mDependView.dismissDialog(mUnLockPwdDialog);
        mDependView.dismissDialog(mRidingMachineDialog);
        mDependView.dismissDialog(mRidingAptitudeDialog);
    }

    @Override
    public void onRefreshRotaAnima(View view) {
        Animation oldAnima = view.getAnimation();
        if(null != oldAnima && !oldAnima.hasEnded()){
            return;
        }
        RotateAnimation anim = new RotateAnimation(0f, 360f * 2,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true);
        anim.setDuration(800);
        anim.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(anim);
    }

    @Override
    public void onCenterMarkerAnima() {
        if(null == mCenterPositionMarker || null == mCenterPositionMarker){
            return;
        }
        jumpPoint(mCenterPositionMarker,mCenterPositionMarker.getPosition());
    }

    @Override
    public void onMarkerRotate(float rotateAngle) {
        if(null != mCurrentPositionMarker){
            mCurrentPositionMarker.setRotateAngle(360 - rotateAngle);
        }
    }

    @Override
    public boolean isHaveBalance() {
        return SharedPreferencesUtil.getUser().getBalance() > 0;
    }

    @Override
    public boolean isPayBalance() {
        return SharedPreferencesUtil.getUser().isPayBalance();
    }

    // 是否为机械锁
    private boolean isMachineLock(String pwd){
        return !TextUtil.isEmpty(pwd);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        Marker marker = walkRouteOverlay.getSelectedMarker();

        if(null == marker || !marker.isInfoWindowShown()){
            LatLng latLng = cameraPosition.target;
            Point mPoint = aMap.getProjection().toScreenLocation(latLng);
            mCenterPositionMarker.setPositionByPixels(mPoint.x,mPoint.y);
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

        Marker marker = walkRouteOverlay.getSelectedMarker();

        if(null == marker || !marker.isInfoWindowShown()){
            mCenterPositionMarker.setPosition(cameraPosition.target);
            onQueryBicycleByGisOfFree();
        }

    }

    private void onMarkerVisible(Marker marker,boolean visible){
        marker.setVisible(visible);
    }
    private void jumpPoint(final Marker marker, final LatLng markerLatlng) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 800;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int i) {
        mDialog.dismiss();
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    final WalkPath walkPath = result.getPaths()
                            .get(0);
                    walkRouteOverlay.setWalkPath(walkPath);
                    walkRouteOverlay.setStartPoint(result.getStartPos());
                    walkRouteOverlay.setEndPoint(result.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    Marker marker = walkRouteOverlay.getSelectedMarker();
                    if(null != marker){
                        Bundle bundle = (Bundle) marker.getObject();
                        bundle.putParcelable(KEY_MARKER_DATA_OPT,walkPath);
                        marker.showInfoWindow();
                    }
                }
            }else{
                mDependView.showMsg("路径规划失败");
            }
        }else{
            mDependView.showMsg("路径规划失败");
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    private void removeWalkLine(){
        walkRouteOverlay.removeFromMap();
        Marker marker = walkRouteOverlay.getSelectedMarker();
        if(null != marker){
            marker.hideInfoWindow();
        }
        walkRouteOverlay.setSelectedMarker(null);
    }

    // 获取 地图标记类型
    private Integer getMarkerType(Marker marker){
        Object obj = marker.getObject();
        if(null != obj){
            Bundle bundle = (Bundle) obj;
            int type = bundle.getInt(KEY_MARKER_TYPE);
            if(type == TYPE_MARKER_BIKE){
                return type;
            }
        }
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        Integer type = getMarkerType(marker);

        if(null != type){
            if(type == TYPE_MARKER_BIKE){
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_marker_bike,null);
                TextView tvNo = view.findViewById(R.id.tv_bikeNum);
                TextView tvTime = view.findViewById(R.id.tv_time);
                TextView tvDistance = view.findViewById(R.id.tv_distance);
                Bundle bundle = (Bundle) marker.getObject();
                LockByGisEntity entity = (LockByGisEntity) bundle.getSerializable(KEY_MARKER_DATA);
                WalkPath walkPath = bundle.getParcelable(KEY_MARKER_DATA_OPT);
                tvNo.setText("车牌号:" + entity.getBicycleNum());
                tvTime.setText(Html.fromHtml("步行<font color='#ff8400'>" + (int)walkPath.getDuration() / 60 + "</font>分钟" ));
                tvDistance.setText(Html.fromHtml("距离<font color='#ff8400'>" + (int) walkPath.getDistance() +"</font>米" ));
                return view;
            }
        }
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
