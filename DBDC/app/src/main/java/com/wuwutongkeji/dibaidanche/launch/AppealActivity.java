package com.wuwutongkeji.dibaidanche.launch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.ChoosePicActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.popup.AppealSucceDialog;
import com.wuwutongkeji.dibaidanche.entity.LoginEntity;
import com.wuwutongkeji.dibaidanche.launch.contract.appeal.AppealContract;
import com.wuwutongkeji.dibaidanche.launch.contract.appeal.AppealPresenter;

import java.io.File;
import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/13.
 */

public class AppealActivity extends BaseToolbarActivity implements AppealContract.AppealBaseView{

    public static final int FRONT_REQ = 10;
    public static final int BACK_REQ = 11;

    public static final String KEY_DATA = "KEY_DATA";

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_idCard)
    TextView tvIdCard;
    @BindView(R.id.img_idcard_front)
    SimpleDraweeView imgIdcardFront;
    @BindView(R.id.btn_card_front)
    RelativeLayout btnCardFront;
    @BindView(R.id.img_idcard_back)
    SimpleDraweeView imgIdcardBack;
    @BindView(R.id.btn_card_back)
    RelativeLayout btnCardBack;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    LoginEntity mAppAuthEntity;

    AppealContract.AppealBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch_appeal;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("用车认证");

        btnCardFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getChoosePicActivity(mContext);
                startActivityForResult(intent, FRONT_REQ);
            }
        });

        btnCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getChoosePicActivity(mContext);
                startActivityForResult(intent, BACK_REQ);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onAuth(mAppAuthEntity);
            }
        });


    }

    @Override
    protected void initData() {

        mAppAuthEntity = (LoginEntity) getIntent().getSerializableExtra(KEY_DATA);

        tvName.setText(mAppAuthEntity.getName());
        tvIdCard.setText(mAppAuthEntity.getIdNumber());

        mPresenter = newPresenter(new AppealPresenter(),this);
    }

    @Override
    public void onShowFrontPic(String path) {
        imgIdcardFront.setImageURI(Uri.fromFile(new File(path)));
    }

    @Override
    public void onShowBackPic(String path) {
        imgIdcardBack.setImageURI(Uri.fromFile(new File(path)));
    }

    @Override
    public AppealContract.AppealBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        new AppealSucceDialog().show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(null == data){
            return;
        }

        String uri = data.getStringExtra(ChoosePicActivity.KEY_PIC);

        if(resultCode == RESULT_OK){

            if(FRONT_REQ == requestCode){
                mPresenter.onForntPic(uri);
            } else if(BACK_REQ == requestCode){
                mPresenter.onBackPic(uri);
            }
        }

    }
}
