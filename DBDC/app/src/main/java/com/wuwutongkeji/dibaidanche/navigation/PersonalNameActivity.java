package com.wuwutongkeji.dibaidanche.navigation;

import android.os.Bundle;
import android.view.View;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.widget.ContainsEmojiEditText;
import com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo.PersonalNameContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.personalInfo.PersonalNamePresenter;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/25.
 */

public class PersonalNameActivity extends BaseToolbarActivity
        implements PersonalNameContract.PersonalNameBaseView {

    @BindView(R.id.edit_name)
    ContainsEmojiEditText editName;

    PersonalNameContract.PersonalNameBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_personal_name;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("修改昵称");
        setRightBtnTitle("保存");
    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new PersonalNamePresenter(), this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    protected boolean showRightTextView() {
        return true;
    }

    @Override
    protected void onRightBtnClickListener(View view) {
        super.onRightBtnClickListener(view);
        mPresenter.onSaveName(editName.getText().toString().trim());
    }

    @Override
    public PersonalNameContract.PersonalNameBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        finish();
    }
}
