package com.wuwutongkeji.dibaidanche.common.widget.refreshlayout.layoutManager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Mr.Bai on 2016/10/14.
 */

public class WrapContentGridManager extends GridLayoutManager {

    public WrapContentGridManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WrapContentGridManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WrapContentGridManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
