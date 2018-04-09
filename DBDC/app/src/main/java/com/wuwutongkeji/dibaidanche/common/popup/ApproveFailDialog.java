package com.wuwutongkeji.dibaidanche.common.popup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.launch.AppealActivity;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/15.
 */
@SuppressLint("ValidFragment")
public class ApproveFailDialog extends BaseDialog {

    @BindView(R.id.icon_close)
    ImageView iconClose;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    LoginEntity mEmtity;

    public ApproveFailDialog(){
        super();
    }

    public ApproveFailDialog(LoginEntity mEmtity) {
        this.mEmtity = mEmtity;
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_approve_fail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        tvPhone.setText(TextUtil.getThumbPhone(mEmtity.getMobile()));

        iconClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getAppealActivity(mContext);
                intent.putExtra(AppealActivity.KEY_DATA,mEmtity);
                startActivity(intent);
                dismiss();
            }
        });

    }

    @Override
    public void initDatas() {

    }

}
