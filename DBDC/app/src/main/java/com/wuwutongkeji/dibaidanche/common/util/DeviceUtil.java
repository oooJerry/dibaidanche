package com.wuwutongkeji.dibaidanche.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

import com.wuwutongkeji.dibaidanche.LaunchApp;
import com.wuwutongkeji.dibaidanche.R;

import java.io.File;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Mr.Bai on 2017/9/12.
 */
public class DeviceUtil {

    /**
     * dp 转化为 px
     *
     * @param dpValue dpValue
     * @return int
     */
    public static int dp2px(float dpValue) {
        final float scale = LaunchApp.Instance.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转化为 dp
     *
     * @param pxValue pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        final float scale = LaunchApp.Instance.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2sp(float dpVal){
        return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                LaunchApp.Instance.getResources().getDisplayMetrics()));
    }

    public static int sp2dp(float spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                LaunchApp.Instance.getResources().getDisplayMetrics());
    }

    public static int sp2px(float spValue) {
        final float fontScale = LaunchApp.Instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 获取设备宽度（px）
     *
     * @return int
     */
    public static int deviceWidth() {
        return LaunchApp.Instance.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     *
     * @return
     */
    public static int deviceHeight() {
        return LaunchApp.Instance.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * SD卡判断
     *
     * @return boolean
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 是否有网
     *
     * @return boolean
     */
    public static boolean isNetworkConnected() {
        if (LaunchApp.Instance != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) LaunchApp.Instance
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /****
     * 获取颜色
     * */
    public static final int getColor(int id) {
        return ContextCompat.getColor(LaunchApp.Instance,id);
    }
    /**
     * 返回版本名字
     * 对应build.gradle中的versionName
     *
     * @return String
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager packageManager = LaunchApp.Instance.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(LaunchApp.Instance.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 获取设备的唯一标识，deviceId
     * imei
     * @return String
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) LaunchApp.Instance.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "-";
        } else {
            return deviceId;
        }
    }

    /***
     * 获取包名
     * @return
     */
    public static String getPackageName(){
        return LaunchApp.Instance.getPackageName();
    }
    /**
     * 获取手机品牌
     *
     * @return String
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return int
     */
    public static int getBuildLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return String
     */
    public static String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    /***
     * 收起键盘
     */
    public static void hideInputKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
        }
    }

    /***
     * 弹出键盘
     */
    public static void showInputKeyBoard(Context mContext) {
        InputMethodManager imm = (InputMethodManager)mContext.
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 获取当前App进程的id
     *
     * @return int
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * 获取当前App进程的Name
     *
     * @param processId processId
     * @return String
     */
    public static String getAppProcessName(int processId) {
        String processName = null;
        ActivityManager am = (ActivityManager) LaunchApp.Instance.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有运行App的进程集合
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = LaunchApp.Instance.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == processId) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e(DeviceUtil.class.getName(), e.getMessage(), e);
            }
        }
        return processName;
    }

    /**
     * 创建App文件夹
     *
     * @param appName     appName
     * @param application application
     * @return String
     */
    public static String createAPPFolder(String appName, Application application) {
        return createAPPFolder(appName, application, null);
    }

    /**
     * 创建App文件夹
     *
     * @param appName     appName
     * @param application application
     * @param folderName  folderName
     * @return String
     */
    public static String createAPPFolder(String appName, Application application, String folderName) {
        File root = Environment.getExternalStorageDirectory();
        File folder;
        /**
         * 如果存在SD卡
         */
        if (DeviceUtil.isSDCardAvailable() && root != null) {
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } else {
            /**
             * 不存在SD卡，就放到缓存文件夹内
             */
            root = application.getCacheDir();
            folder = new File(root, appName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        if (folderName != null) {
            folder = new File(folder, folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }
        return folder.getAbsolutePath();
    }

    /**
     * 通过Uri找到File
     *
     * @param context context
     * @param uri     uri
     * @return File
     */
    public static File uri2File(Activity context, Uri uri) {
        File file;
        String[] project = {MediaStore.Images.Media.DATA};
        Cursor actualImageCursor = context.getContentResolver().query(uri, project, null, null, null);
        if (actualImageCursor != null) {
            int actual_image_column_index = actualImageCursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualImageCursor.moveToFirst();
            String img_path = actualImageCursor
                    .getString(actual_image_column_index);
            file = new File(img_path);
        } else {
            file = new File(uri.getPath());
        }
        if (actualImageCursor != null) actualImageCursor.close();
        return file;
    }

    /**
     * 获取AndroidManifest.xml里 <meta-data>的值
     *
     * @param name    name
     * @return String
     */
    public static String getMetaData(String name) {
        String value = null;
        try {
            ApplicationInfo appInfo = LaunchApp.Instance.getPackageManager().getApplicationInfo(LaunchApp.Instance.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 复制到剪贴板
     *
     * @param content content
     */
    public static void copy2Clipboard(String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) LaunchApp.Instance.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(LaunchApp.Instance.getString(R.string.app_name), content);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) LaunchApp.Instance.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content);
        }
    }
    public static String getCachePath() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = LaunchApp.Instance.getExternalCacheDir();
        else
            cacheDir = LaunchApp.Instance.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }

    /**
     * 获取IP地址
     * @return
     */
    public static String getIpAddress(){
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }
    public static String getFileSize(File file){
        String size = "0KB";
        if(file.exists() && file.isFile()){
            long fileS = file.length();
            DecimalFormat df = new DecimalFormat("#.00");
            if (fileS < 1024) {
                size = df.format((double) fileS) + "BT";
            } else if (fileS < 1048576) {
                size = df.format((double) fileS / 1024) + "KB";
            } else if (fileS < 1073741824) {
                size = df.format((double) fileS / 1048576) + "MB";
            } else {
                size = df.format((double) fileS / 1073741824) +"GB";
            }
        }else if(file.exists() && file.isDirectory()){
            size = "0KB";
        }else{
            size = "0BT";
        }
        return size;
    }
}
