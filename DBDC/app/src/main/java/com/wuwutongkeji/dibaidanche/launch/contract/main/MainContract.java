package com.wuwutongkeji.dibaidanche.launch.contract.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.route.RouteSearch;
import com.wuwutongkeji.dibaidanche.base.BaseDependView;
import com.wuwutongkeji.dibaidanche.base.BasePresenter;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.WalletDepositQueryEntity;

/**
 * Created by Mr.Bai on 2017/9/13.
 */

public interface MainContract {

    abstract class MainBasePresenter extends BasePresenter<MainBaseView>
            implements AMap.OnCameraChangeListener, RouteSearch.OnRouteSearchListener,AMap.InfoWindowAdapter{

        public abstract void onCreate(Bundle outState);

        public abstract void onSaveInstanceState(Bundle outState);
        // 检查是否需要弹出登录弹框
        public abstract void checkUserStateDialog(Context mContext);
        // 检查是否包含 卡(年卡、月卡、....)
        public abstract void checkHavfreeCard();
        // 检查是否包含 卡(年卡、月卡、....)
        public abstract boolean checkHavfreeCards();
        // 根据用户状态返回对应的界面
        public abstract Intent getIntentByUserState(Context mContext);
        // 获取用户状态
        public abstract AppConfig.UserState getUserState();
        //更新用户显示信息
        public abstract void onUpdatePersonalInfo(LoginEntity entity);
        // 关闭侧滑
        public abstract boolean closeDrawer(DrawerLayout drawer);
        // 退出app
        public abstract boolean exitApp();
        // 移动地图到当前位置
        public abstract void onMoveMapLocation();
        // 移动中心位置到当前位置
        public abstract void onCenter2Current();
        //查询空闲车辆
        public abstract void onQueryBicycleByGisOfFree();
        //退出登录
        public abstract void onLogout(Context mContext);
        // 查询骑行锁的类型
        public abstract void queryRidingLockType(WalletDepositQueryEntity queryEntity);
        // 退押金
        public abstract void onDepositRefund(Context mContext);
        // 充值余额
        public abstract void onWalletRecharge(long balance);
        //开锁
        public abstract void onOpenLock(LockEntity entity);
        //开锁 或 关锁
        public abstract void onAptitudeLockOption(AppConfig.LockType type);
        // 关锁
        public abstract void onCloseLock();
        // 刷新动画
        public abstract void onRefreshRotaAnima(View view);
        // 中心点Marker 动画
        public abstract void onCenterMarkerAnima();
        // 当前点Marker 旋转
        public abstract void onMarkerRotate(float rotateAngle);
        // 是否还有余额
        public abstract boolean isHaveBalance();
        // 是否完成首冲
        public abstract boolean isPayBalance();
    }

    interface MainBaseView extends BaseDependView<MainBasePresenter> {

        // 增加 标记
        Marker onAddMarker(MarkerOptions markerOptions);

        void onMoveMapLocation(CameraUpdate cameraUpdate);

        void showDialog(Fragment mDialog);

        void dismissDialog(Fragment mDialog);

        void onUpdatePersonalInfo(LoginEntity entity);

    }

}
