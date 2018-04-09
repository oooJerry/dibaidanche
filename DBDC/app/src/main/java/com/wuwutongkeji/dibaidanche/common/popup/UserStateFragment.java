package com.wuwutongkeji.dibaidanche.common.popup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/27.
 */

public class UserStateFragment extends BaseFragment {

    @BindView(R.id.btn_jump)
    LinearLayout btnJump;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    String msg;
    Intent mIntent;


    public void setData(String msg, Intent intent){
        this.msg = msg;
        if(null != tvMsg){
            tvMsg.setText(msg);
        }
        this.mIntent = intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_state;
    }

    @Override
    protected void initToolbar(View ChildView, Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(View ChildView, Bundle savedInstanceState) {
        tvMsg.setText(msg);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mIntent);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(UserStateFragment.this)
                        .commitAllowingStateLoss();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
