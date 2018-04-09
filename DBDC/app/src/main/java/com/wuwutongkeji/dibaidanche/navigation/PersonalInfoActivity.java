package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.ChoosePicActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.entity.LogoutEntity;
import com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo.PersonalInfoContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo.PersonalInfoPresenter;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/25.
 */

public class PersonalInfoActivity extends BaseToolbarActivity implements
        PersonalInfoContract.PersonalInfoBaseView{

    static final int REQ_CODE_CHOOSE = 10;

    @BindView(R.id.icon_header)
    SimpleDraweeView iconHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_credit)
    TextView tvCredit;
    @BindView(R.id.btn_credit)
    LinearLayout btnCredit;
    @BindView(R.id.tv_nikeName)
    TextView tvNikeName;
    @BindView(R.id.btn_nikeName)
    LinearLayout btnNikeName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_bindPhoneNum)
    TextView tvBindPhoneNum;
    @BindView(R.id.tv_auth)
    TextView tvAuth;
    @BindView(R.id.button)
    Button button;

    PersonalInfoContract.PersonalInfoBasePresenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_personal_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("个人信息");

        btnNikeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getPersonalNameActivity(mContext));
            }
        });

        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getCreditActivity(mContext));
            }
        });

        iconHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getChoosePicActivity(mContext);
                startActivityForResult(intent,REQ_CODE_CHOOSE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtil.clearAll();
                EventBus.getDefault().post(new LogoutEntity());
                finish();
            }
        });
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new PersonalInfoPresenter(),this);
    }

    @Override
    public PersonalInfoContract.PersonalInfoBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onLoadUserInfo();
    }

    @Override
    public void onLoadUserInfo(LoginEntity entity) {
        iconHeader.setImageURI(entity.getPhotoUrl());
        tvName.setText(entity.getNickname());
        tvCredit.setText("信用分：" + entity.getCredit());
        tvNikeName.setText(entity.getNickname());
        if(!TextUtil.isEmpty(entity.getGender())){
            tvGender.setText("MALE".equals(entity.getGender()) ? "男":"女");
        }
        if(!TextUtil.isEmpty(entity.getBirth())){
            tvBirthday.setText(DateUtil.dateFormat(
                    entity.getBirth(),
                    DateUtil.yyyy_MM_dd_HH__mm__ss,
                    DateUtil.yyyy_MM_dd));
        }
        tvBindPhoneNum.setText(entity.getMobile());
        tvAuth.setText(entity.isAuthId() ? "已认证":"未认证");
    }

    @Override
    public void onLoadUserInfoSucc(String url) {
        iconHeader.setImageURI(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(null == data){
            return;
        }

        String uri = data.getStringExtra(ChoosePicActivity.KEY_PIC);

        if(resultCode == RESULT_OK){

            if(REQ_CODE_CHOOSE == requestCode){
                mPresenter.uploadFile(uri);
            }
        }
    }
}
