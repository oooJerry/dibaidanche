package com.wuwutongkeji.dibaidanche.common.popup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.util.BlueToothUtil;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/25.
 */

public class UnLockBlueToothDialog extends BaseDialog {

    @BindView(R.id.btn_cancle)
    Button btnCancle;
    @BindView(R.id.btn_open)
    Button btnOpen;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_unlock_bluetooth;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getScanCodeUnlockActivity(mContext));
                dismiss();
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlueToothUtil.enable();
                startActivity(AppIntent.getScanCodeUnlockActivity(mContext));
                dismiss();
            }
        });
    }

    @Override
    public void initDatas() {

    }

}
