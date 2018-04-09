package com.wuwutongkeji.dibaidanche.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;

import java.lang.reflect.Field;

import butterknife.BindView;


/**
 * Created by Mr.Bai on 2017/9/7.
 */
public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.app_bar_layout)
    protected AppBarLayout mAppBarLayout;
    @BindView(R.id.btn_close)
    protected LinearLayout btnClose;
    @BindView(R.id.icon_close)
    protected ImageView iconClose;
    @BindView(R.id.tv_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_right)
    protected TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRightBtnClickListener(view);
            }
        });
    }

    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState savedInstanceState
     */


    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        this.initToolbarHelper();
        if(this.mToolbar == null){
            return;
        }

        tvRight.setVisibility(showRightTextView() ? View.VISIBLE:View.GONE);
        hideBack();
    }
    /**
     * init the toolbar
     */
    protected void initToolbarHelper() {
        if (this.mToolbar == null || this.mAppBarLayout == null)
            return;
        mToolbar.setTitle("");
        this.setSupportActionBar(this.mToolbar);
    }
    protected void setElevation(float elevation){
        if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout.setElevation(elevation);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    protected void showBack() {
        if (this.mAppBarLayout != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected void hideBack() {
        if (this.mAppBarLayout != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    protected void useArrowBackIcon(){
        if(null != mToolbar){
            iconClose.setImageResource(R.mipmap.icon_arrow_left_close);
        }
    }

    protected boolean showRightTextView(){
        return false;
    }

    protected void setTitle(String title){
        tvTitle.setText(title);
    }

    protected void setRightBtnTitle(String title){
        tvRight.setText(title);
    }

    protected void onRightBtnClickListener(View view){

    }
}
