package com.wuwutongkeji.dibaidanche.common.widget.refreshlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.layoutManager.WrapContentLinearManager;


/**
 * Created by Mr.Bai on 2017/9/25.
 */

public class RefreshLayout extends LinearLayout {

    RecyclerView scrollView;

    SwipeToLoadLayout swipeToLoadLayout;

    public RefreshLayout(Context context) {
        this(context,null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroll(attrs);
    }
    public void initScroll(AttributeSet attrs){

        LayoutInflater.from(getContext()).inflate(R.layout.layout_widget_refresh_google_style,this,true);

        boolean refreshEnable = true;
        boolean loadMoreEnable = true;

        if(null != attrs){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RefreshLayout);
            refreshEnable = array.getBoolean(R.styleable.RefreshLayout_refreshEnabled,true);
            loadMoreEnable = array.getBoolean(R.styleable.RefreshLayout_loadMoreEnabled,true);

            array.recycle();
        }

        final WrapContentLinearManager wrapContentLinearManager = new WrapContentLinearManager(getContext());

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        scrollView = (RecyclerView) findViewById(R.id.swipe_target);
        scrollView.setLayoutManager(wrapContentLinearManager);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(null != onRefreshListener){
                    onRefreshListener.onRefresh(RefreshLayout.this);
                }
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(null != onRefreshListener){
                    onRefreshListener.onLoadMore(RefreshLayout.this);
                }
            }
        });

        scrollView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });

        setLoadMoreEnabled(loadMoreEnable);
        setRefreshEnabled(refreshEnable);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        scrollView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        scrollView.setAdapter(adapter);
    }

    public RecyclerView getRecyclerView(){
        return  scrollView;
    }
    public void firstRefresh(){
        setRefreshing(true);
        if(null != onRefreshListener){
            onRefreshListener.onRefresh(this);
        }
    }

    public void setRefreshing(boolean refreshing){
        swipeToLoadLayout.setRefreshing(refreshing);
    }

    public void refreshAndLoadMoreStop(){
        if(swipeToLoadLayout.isRefreshing()){
            swipeToLoadLayout.setRefreshing(false);
        }
        if(swipeToLoadLayout.isLoadingMore()){
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    public void setLoadMoreEnabled(boolean loadMoreEnable){
        swipeToLoadLayout.setLoadMoreEnabled(loadMoreEnable);
    }
    public void setRefreshEnabled(boolean refreshEnable){
        swipeToLoadLayout.setRefreshEnabled(refreshEnable);
    }

    RefreshListener onRefreshListener;

    public void setOnRefreshListener(RefreshListener onRefreshListener){
        this.onRefreshListener = onRefreshListener;
    }

    public interface RefreshListener{

        void onRefresh(RefreshLayout layout);

        void onLoadMore(RefreshLayout layout);
    }
}
