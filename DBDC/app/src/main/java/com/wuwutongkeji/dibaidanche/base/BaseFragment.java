package com.wuwutongkeji.dibaidanche.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.wuwutongkeji.dibaidanche.LaunchApp;
import com.wuwutongkeji.dibaidanche.common.popup.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mr.Bai on 2017/9/7.
 */
public abstract class BaseFragment extends Fragment {

    protected String mSimpleName = getClass().getSimpleName();

    protected View ChildView;

    protected Context mContext;

    protected Dialog mLoadingDialog;

    private boolean isShowLoadingDialog;

    Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.ChildView == null) {
            this.ChildView = inflater.inflate(this.getLayoutId(), container, false);
        }
        if (this.ChildView.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.ChildView.getParent();
            parent.removeView(this.ChildView);
        }
        mUnbinder = ButterKnife.bind(this, ChildView);
        return ChildView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        BaseAppCompatActivity activity = (BaseAppCompatActivity) getActivity();

        if(getActivity()==null){
            return;
        }
        if(getActivity().isFinishing()){
            return;
        }

        mLoadingDialog = new LoadingDialog(mContext);
        mLoadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                isShowLoadingDialog = true;
            }
        });

        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isShowLoadingDialog = false;
            }
        });

        initViews(ChildView, savedInstanceState);
        initToolbar(ChildView, savedInstanceState);
        initData();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isShowLoadingDialog){
            mLoadingDialog.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isShowLoadingDialog){
            mLoadingDialog.dismiss();
        }
    }

    public <T extends BasePresenter,V extends BaseDependView> T  newPresenter(T t ,V v){
        return (T) t.newBuilder()
                .proceed(v)
                .dialog(mLoadingDialog)
                .build();
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findViewById(int id) {
        return (V) this.ChildView.findViewById(id);
    }

    protected void startActivity(Intent intent, View... views){

        Pair[] pairs = new Pair[views.length];

        for(int i = 0 ; i < views.length ; i ++){
            pairs[i] = new Pair<>(views[i], ViewCompat.getTransitionName(views[i]));
        }
        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);

        ActivityCompat.startActivity(mContext,
                intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        RefWatcher refWatcher = LaunchApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    public void showMsg(String msg){
        Snackbar.make(ChildView,msg, Snackbar.LENGTH_SHORT).show();
    }

    protected void finish(){
        getActivity().finish();
    }

    protected abstract int getLayoutId();

    protected abstract void initToolbar(View ChildView, Bundle savedInstanceState);

    protected abstract void initViews(View ChildView, Bundle savedInstanceState);

    protected abstract void initData();

}
