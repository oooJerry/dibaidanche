package com.wuwutongkeji.dibaidanche.common.manager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wuwutongkeji.dibaidanche.LaunchApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Bai on 2017/9/7.
 */
public class LocationManager {

    private static AMapLocation mAMapLocation;
    private AMapLocationClient locationClient;
    private static LocationManager instance;

    public static synchronized LocationManager initialize(){
        if(instance == null){
            instance = new LocationManager();
        }
        return instance;
    }
    private LocationManager(){
        initLocationClient();
    }

    /**
     * 初始化高德定位
     */
    private void initLocationClient(){
        //初始化client
        locationClient = new AMapLocationClient(LaunchApp.Instance);
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

        locationClient.startLocation();
    }
    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        return mOption;
    }
    public static AMapLocation getAMapLocation(){
        return mAMapLocation;
    }

    public static String getAddress(){
        if(null != mAMapLocation){
            return mAMapLocation.getAddress();
        }
        return null;
    }

    public static String getLongitude(){
        if(null != mAMapLocation){
            return String.valueOf(mAMapLocation.getLongitude());
        }
        return "-999";
    }

    public static double getDLongitude(){
        if(null != mAMapLocation){
            return mAMapLocation.getLongitude();
        }
        return -999;
    }

    public static String getLatitude(){
        if(null != mAMapLocation){
            return String.valueOf(mAMapLocation.getLatitude());
        }
        return "-999";
    }

    public static double getDLatitude(){
        if(null != mAMapLocation){
            return mAMapLocation.getLatitude();
        }
        return -999;
    }
    /****
     * sb.append("定位类型: " + location.getLocationType() + "\n");
     sb.append("经    度    : " + location.getLongitude() + "\n");
     sb.append("纬    度    : " + location.getLatitude() + "\n");
     sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
     sb.append("提供者    : " + location.getProvider() + "\n");

     if (location.getProvider().equalsIgnoreCase(
     android.location.LocationManager.GPS_PROVIDER)) {
     // 以下信息只有提供者是GPS时才会有
     sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
     sb.append("角    度    : " + location.getBearing() + "\n");
     // 获取当前提供定位服务的卫星个数
     sb.append("星    数    : "
     + location.getSatellites() + "\n");
     } else {
     // 提供者是GPS时是没有以下信息的
     sb.append("国    家    : " + location.getCountry() + "\n");
     sb.append("省            : " + location.getProvince() + "\n");
     sb.append("市            : " + location.getCity() + "\n");
     sb.append("城市编码 : " + location.getCityCode() + "\n");
     sb.append("区            : " + location.getDistrict() + "\n");
     sb.append("区域 码   : " + location.getAdCode() + "\n");
     sb.append("地    址    : " + location.getAddress() + "\n");
     sb.append("兴趣点    : " + location.getPoiName() + "\n");
     //定位完成的时间
     sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss:sss") + "\n");
     }
     {} else
     //定位失败
     sb.append("定位失败" + "\n");
     sb.append("错误码:" + location.getErrorCode() + "\n");
     sb.append("错误信息:" + location.getErrorInfo() + "\n");
     sb.append("错误描述:" + location.getLocationDetail() + "\n");
     }
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
             if (null != loc) {
                //解析定位结果
                mAMapLocation = loc;
                for(AMapLocationListener mListener: mLocationListenerList){
                    mListener.onLocationChanged(mAMapLocation);
                }
            }
        }
    };

    List<AMapLocationListener> mLocationListenerList = new ArrayList<>();

    public boolean removeListener(AMapLocationListener mListener){
        return mLocationListenerList.remove(mListener);
    }

    public void addLocationListener(AMapLocationListener mListener){
        if(!mLocationListenerList.contains(mListener)){
            mLocationListenerList.add(mListener);
        }
    }
}
