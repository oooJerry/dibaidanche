package com.wuwutongkeji.dibaidanche.bike;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.bike.adapter.FeedbackAdapter;
import com.wuwutongkeji.dibaidanche.bike.contract.feedback.BikeFeedbackContract;
import com.wuwutongkeji.dibaidanche.bike.contract.feedback.BikeFeedbackPresenter;
import com.wuwutongkeji.dibaidanche.common.activity.ChoosePicActivity;
import com.wuwutongkeji.dibaidanche.common.adapter.ChoosePicAdapter;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.widget.ContainsEmojiEditText;
import com.wuwutongkeji.dibaidanche.common.widget.layoutManager.MGridLayoutManager;
import com.wuwutongkeji.dibaidanche.entity.OptionTypeEntity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/24.
 */

public class  BikeFeedbackActivity extends BaseToolbarActivity implements BikeFeedbackContract.BikeFeedbackBaseView {

    public static final int MAX_NUM = 4;
    public static final int PIC_REQ = 10;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edit_bikeNo)
    ContainsEmojiEditText editBikeNo;
    @BindView(R.id.edit_suggestion)
    ContainsEmojiEditText editSuggestion;
    @BindView(R.id.picRecyclerView)
    RecyclerView picRecyclerView;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_cell)
    TextView btnCell;

    FeedbackAdapter mAdapter;
    ChoosePicAdapter mChooseAdapter;

    BikeFeedbackContract.BikeFeedbackBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bike_feedback;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("反馈");


        btnCell.setText(Html.fromHtml("客服电话: " +
                "<font color='#5DA5FC'>"+ getString(R.string.phone_customer)+"</font>"));

        recyclerView.setLayoutManager(new MGridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter = new FeedbackAdapter(mContext));

        picRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        picRecyclerView.setAdapter(mChooseAdapter = new ChoosePicAdapter(mContext, MAX_NUM));

        btnCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getTelActivity(getString(R.string.phone_customer)));
            }
        });

        mChooseAdapter.setOnChoosePicListener(new ChoosePicAdapter.ChoosePicListener() {
            @Override
            public void onAddPic() {
                startActivityForResult(AppIntent.getChoosePicActivity(mContext), PIC_REQ);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bicycleNum = editBikeNo.getText().toString().trim();
                String content = editSuggestion.getText().toString().trim();
                mPresenter.onSubmit(bicycleNum,mAdapter.getType(),content,mChooseAdapter.getData());
            }
        });

        editBikeNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onEnableofSubmit();
            }
        });
        mAdapter.setOnFeedbackListener(new FeedbackAdapter.FeedbackListener() {
            @Override
            public void onItem(int type) {
                onEnableofSubmit();
            }
        });


    }

    private void onEnableofSubmit(){
        btnSubmit.setEnabled(0 != mAdapter.getType() && editBikeNo.getText().toString().trim().length() == 6);
    }

    @Override
    protected void initData() {

        mPresenter = newPresenter(new BikeFeedbackPresenter(), this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PIC_REQ) {
                String url = data.getStringExtra(ChoosePicActivity.KEY_PIC);
                mChooseAdapter.LoadMore(Uri.fromFile(new File(url)));
            }
        }
    }


    @Override
    public void onLoadOption(List<OptionTypeEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public BikeFeedbackContract.BikeFeedbackBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        finish();
    }
}
