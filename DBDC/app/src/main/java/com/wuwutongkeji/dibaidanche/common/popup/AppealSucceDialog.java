package com.wuwutongkeji.dibaidanche.common.popup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/18.
 */

public class AppealSucceDialog extends BaseDialog {

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_appeal_succe;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initDatas() {

    }

}
