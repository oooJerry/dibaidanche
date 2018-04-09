package com.wuwutongkeji.dibaidanche.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.leakcanary.RefWatcher;
import com.wuwutongkeji.dibaidanche.LaunchApp;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.popup.LoadingDialog;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Mr.Bai on 2017/9/15.
 *
 * 此类参考 NiceDialog
 *
 * 链接：https://github.com/Othershe/NiceDialog
 *
 */

public abstract class BaseDialog extends DialogFragment {

    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String SHOWGRAVITY = "show_Gravity";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0f;//灰度深浅
    private int showGravity;//显示位置
    private boolean outCancel = false;//是否点击外部取消

    protected View mDecoView;

    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    protected Context mContext;

    protected Dialog mLoadingDialog;

    private boolean isShowLoadingDialog;

    Unbinder mUnbinder;

    public abstract int intLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initDatas();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
        layoutId = intLayoutId();

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            showGravity = savedInstanceState.getInt(SHOWGRAVITY);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDecoView = inflater.inflate(layoutId, container, false);
        mUnbinder = ButterKnife.bind(this,mDecoView);
        mDecoView.setBackgroundColor(Color.parseColor("#b3000000"));
        return mDecoView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                isShowLoadingDialog = true;
            }
        });

        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShowLoadingDialog = false;
            }
        });

        initViews(savedInstanceState);
        initDatas();
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
        if(isShowLoadingDialog){
            mLoadingDialog.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isShowLoadingDialog){
            mLoadingDialog.dismiss();
        }
    }
    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(SHOWGRAVITY, showGravity);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    protected void initParams() {
        if(null == getDialog()){
            return;
        }
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0f
            lp.dimAmount = dimAmount;
            lp.gravity = showGravity;

            //设置dialog宽度
            if (width == 0) {
                lp.width = DeviceUtil.deviceWidth() - 2 * DeviceUtil.dp2px(margin);
            } else if (width == -1) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = DeviceUtil.dp2px(width);
            }

            //设置dialog高度
            if(height == 0) {
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            }else if (height == -1) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.height = DeviceUtil.dp2px( height);
            }

            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }

    public BaseDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialog setShowGravity(int gravity) {
        this.showGravity = gravity;
        return this;
    }

    public BaseDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseDialog show(FragmentManager manager) {
        try {
            Field mDismissedField = getClass().getSuperclass().getSuperclass().getDeclaredField("mDismissed");
            mDismissedField.setAccessible(true);
            mDismissedField.set(this,false);


            Field mmShownByMeField = getClass().getSuperclass().getSuperclass().getDeclaredField("mShownByMe");
            mmShownByMeField.setAccessible(true);
            mmShownByMeField.set(this,true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }
    public void showMsg(String msg){
        Snackbar.make(mDecoView,msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        RefWatcher refWatcher = LaunchApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
        super.onDestroy();
    }
}
