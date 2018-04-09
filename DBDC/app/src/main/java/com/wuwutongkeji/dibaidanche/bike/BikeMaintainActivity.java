package com.wuwutongkeji.dibaidanche.bike;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.bike.adapter.MaintainAdapter;
import com.wuwutongkeji.dibaidanche.bike.contract.maintain.BikeMaintainContract;
import com.wuwutongkeji.dibaidanche.bike.contract.maintain.BikeMaintainPresenter;
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

public class BikeMaintainActivity extends BaseToolbarActivity implements BikeMaintainContract.BikeMaintainBaseView {

    public static final String KEY_DATA = "KEY_DATA";

    public static final int MAX_NUM = 4;
    public static final int PIC_REQ = 10;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edit_suggestion)
    ContainsEmojiEditText editSuggestion;
    @BindView(R.id.picRecyclerView)
    RecyclerView picRecyclerView;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_cell)
    TextView btnCell;

    MaintainAdapter mAdapter;
    ChoosePicAdapter mChooseAdapter;

    BikeMaintainContract.BikeMaintainBasePresenter mPresenter;

    String bicycleNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bike_maintain;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("报修");

        btnCell.setText(Html.fromHtml("客服电话: " +
                "<font color='#5DA5FC'>"+ getString(R.string.phone_customer)+"</font>"));

        recyclerView.setLayoutManager(new MGridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter = new MaintainAdapter(mContext));

        picRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        picRecyclerView.setAdapter(mChooseAdapter = new ChoosePicAdapter(mContext,MAX_NUM));

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
                String content = editSuggestion.getText().toString().trim();
                mPresenter.onSubmit(bicycleNum,mAdapter.getType(),content,mChooseAdapter.getData());
            }
        });

        mAdapter.setOnMaintainListener(new MaintainAdapter.MaintainListener() {
            @Override
            public void onItem(int type) {
                btnSubmit.setEnabled(true);
            }
        });
    }

    @Override
    protected void initData() {

        bicycleNum = getIntent().getStringExtra(KEY_DATA);

        mPresenter = newPresenter(new BikeMaintainPresenter(),this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == PIC_REQ){
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
    public BikeMaintainContract.BikeMaintainBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {
        finish();
    }
}
