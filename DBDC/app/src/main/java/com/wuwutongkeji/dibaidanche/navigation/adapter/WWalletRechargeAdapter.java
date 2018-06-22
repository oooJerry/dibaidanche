package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.navigation.FreeCardActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WWalletRechargeAdapter extends BaseRecyclerAdapter
        <WWalletRechargeAdapter.ViewHolder, Long> {

    private boolean state;

    //自定义监听事件
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public WWalletRechargeAdapter(Context context, boolean state) {
        super(context);
        this.state = state;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(
                R.layout.adapter_navigation_wallet_recharge, parent, false));
    }

    private int index;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Long data = mData.get(position);

        if (state) {
            holder.btnRecharge.setSelected(index == position);
        } else {
            holder.btnRecharge.setSelected(false);
        }
//        if (position == 0) {
//            holder.btnRecharge.setText("365天=" + ((double)data / 100) + "元");
//        }
//        if (position == 1) {
        holder.btnRecharge.setText("180天=" + ((double) data / 100) + "元");
        FreeCardActivity.freeCardTypeId = "3";

//        }
//        if (position == 2) {
//            holder.btnRecharge.setText("90天=" + ((double)data / 100) + "元");
//        }
//        if (position == 3) {
//            holder.btnRecharge.setText("30天=" + ((double)data / 100) + "元");
//        }

        holder.btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = true;
                index = position;
                if (position == 1) {
                    FreeCardActivity.freeCardTypeId = "3";
                } else if (position == 3) {
                    FreeCardActivity.freeCardTypeId = "1";
                } else {
                    FreeCardActivity.freeCardTypeId = position + "";
                }
                notifyDataSetChanged();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    public long getSelectedData() {
        return mData.get(index);
    }

    public void getSelectedState() {
        state = false;
        notifyDataSetChanged();
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.btn_recharge)
        Button btnRecharge;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
