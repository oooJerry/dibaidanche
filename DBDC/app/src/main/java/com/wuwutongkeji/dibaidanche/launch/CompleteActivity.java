package com.wuwutongkeji.dibaidanche.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/13.
 */

public class CompleteActivity extends BaseToolbarActivity {

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_complete;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("立即用车");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
