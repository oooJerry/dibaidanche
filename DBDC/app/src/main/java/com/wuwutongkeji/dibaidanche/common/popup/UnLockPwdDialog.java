package com.wuwutongkeji.dibaidanche.common.popup;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.widget.InputNumView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/20.
 */

public class UnLockPwdDialog extends BaseDialog {

    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.inputNumber)
    InputNumView inputNumber;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    String mText;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_unlock_pwd;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        inputNumber.setTxt(mText);
    }

    @Override
    public void initDatas() {

    }
    public void setPwd(String txt){
        mText = txt;
    }
}
