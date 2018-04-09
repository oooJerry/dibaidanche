package com.wuwutongkeji.dibaidanche.common.util;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Mr.Bai on 17/9/25.
 */

public class BlueToothUtil {

    static BluetoothAdapter mBluetoothAdapter;

    static {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static boolean isEnable(){
        return mBluetoothAdapter.isEnabled();
    }

    public static void enable(){
        if(!isEnable()){
            mBluetoothAdapter.enable();
        }
    }



}
