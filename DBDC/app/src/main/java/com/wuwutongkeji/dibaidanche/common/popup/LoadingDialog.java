package com.wuwutongkeji.dibaidanche.common.popup;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.wuwutongkeji.dibaidanche.R;

/**
 * Created by Mr.Bai on 17/9/26.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.BaseDialog);
        setContentView(R.layout.dialog_loading);
        setCancelable(false);

        ImageView imgLoading = (ImageView) findViewById(R.id.icon_loading);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_loading);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        imgLoading.startAnimation(animation);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }
}
