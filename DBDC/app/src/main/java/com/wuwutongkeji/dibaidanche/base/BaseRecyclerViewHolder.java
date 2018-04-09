package com.wuwutongkeji.dibaidanche.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 2017/9/7.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private int lastPosition;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
