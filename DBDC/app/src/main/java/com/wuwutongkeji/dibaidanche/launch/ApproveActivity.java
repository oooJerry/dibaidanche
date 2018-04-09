package com.wuwutongkeji.dibaidanche.launch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.popup.ApproveFailDialog;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.common.widget.ContainsEmojiEditText;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.launch.contract.approve.ApproveContract;
import com.wuwutongkeji.dibaidanche.launch.contract.approve.ApprovePresenter;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/13.
 */

public class ApproveActivity extends BaseToolbarActivity implements ApproveContract.ApproveBaseView {

    @BindView(R.id.edit_name)
    ContainsEmojiEditText editName;
    @BindView(R.id.edit_idCard)
    ContainsEmojiEditText editIdCard;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    ApproveContract.ApproveBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_approve;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("实名认证");

        editName.addTextChangedListener(mInputTextWather);
        editIdCard.addTextChangedListener(mInputTextWather);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onAuth(editIdCard.getText().toString().trim(),
                        editName.getText().toString().trim());
            }
        });

    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new ApprovePresenter(),this);

    }

    @Override
    public void onShowApproveFail(LoginEntity entity) {
        new ApproveFailDialog(entity).show(getSupportFragmentManager());
    }

    @Override
    public ApproveContract.ApproveBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        Intent intent = AppConfig.user2Intent((LoginEntity) serializable,mContext);
        if(null != intent){
            startActivity(intent);
        }else{
            startActivity(AppIntent.getCompleteActivity(mContext));
        }
        finish();
    }

    TextWatcher mInputTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            String name = editName.getText().toString();
            String idCard = editIdCard.getText().toString();
            btnSubmit.setEnabled(TextUtil.isIdcard(idCard) &&
                    !TextUtil.isEmpty(name));
        }
    };
}
