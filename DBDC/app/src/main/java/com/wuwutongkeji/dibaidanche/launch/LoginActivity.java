package com.wuwutongkeji.dibaidanche.launch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.common.widget.ContainsEmojiEditText;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.launch.contract.login.LoginContract;
import com.wuwutongkeji.dibaidanche.launch.contract.login.LoginPresenter;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/7.
 */
public class LoginActivity extends BaseToolbarActivity implements LoginContract.LoginBaseView{

    @BindView(R.id.edit_phone)
    ContainsEmojiEditText editPhone;
    @BindView(R.id.edit_verifyCode)
    ContainsEmojiEditText editVerifyCode;
    @BindView(R.id.btn_sendCode)
    Button btnSendCode;
    @BindView(R.id.edit_inviteCode)
    ContainsEmojiEditText editInviteCode;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_clause)
    TextView btnClause;


    LoginContract.LoginBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("手机验证");

        editPhone.addTextChangedListener(mInputTextWatcher);
        editVerifyCode.addTextChangedListener(mInputTextWatcher);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendVerifyCode(editPhone.getText().toString());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(
                        editPhone.getText().toString().trim(),
                        editVerifyCode.getText().toString().trim(),
                        editInviteCode.getText().toString().trim());
            }
        });

        btnClause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE,"用车服务条款");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.USER_PROTOCOL);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void initData() {

        mPresenter = newPresenter(new LoginPresenter(),this);
    }

    @Override
    public void timerofCode(boolean enable, String timeCode) {
        btnSendCode.setEnabled(enable);
        btnSendCode.setText(enable ? "获取验证码" : timeCode + "秒后重发");
    }

    @Override
    public LoginContract.LoginBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        Intent intent = AppConfig.user2Intent((LoginEntity) serializable,mContext);
        if(null != intent){
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroyTimer();
        super.onDestroy();
    }
    TextWatcher mInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String phone = editPhone.getText().toString().trim();
            String verifyCode = editVerifyCode.getText().toString().trim();

            if(!mPresenter.timerIsRun()){
                btnSendCode.setEnabled(TextUtil.isCheckPhone(phone));
            }
            btnSubmit.setEnabled(TextUtil.isCheckPhone(phone) && !TextUtil.isEmpty(verifyCode));
        }
    };
}
