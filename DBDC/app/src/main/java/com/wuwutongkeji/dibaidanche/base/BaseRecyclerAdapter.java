package com.wuwutongkeji.dibaidanche.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Bai on 2017/9/7.
 */
public abstract class BaseRecyclerAdapter <VH extends BaseRecyclerViewHolder,T>
        extends RecyclerView.Adapter<VH> {

    protected LayoutInflater mLayoutInflater;

    protected List<T> mData;

    protected Context mContext;

    public BaseRecyclerAdapter(Context context){
        this(context,new ArrayList<T>());
    }
    public BaseRecyclerAdapter(Context context, List<T> data){
        mContext = context;
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void update(List<T> data){
        mData.clear();
        if(null != data){
            mData.addAll(data);
        }
//        notifyItemRangeInserted(0,mData.size());
        notifyDataSetChanged();
    }
    public void LoadMore(T data){
        if(null != data){
            mData.add(data);
        }
        notifyDataSetChanged();
    }
    public void LoadMore(List<T> data){
        if(null != data){
            mData.addAll(data);
        }
        notifyDataSetChanged();
//        notifyItemRangeChanged(mData.size() - data.size(),mData.size());
    }
    public void remove(T data){
        mData.remove(data);
        notifyDataSetChanged();
    }
    public List<T> getData(){
        return mData;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
