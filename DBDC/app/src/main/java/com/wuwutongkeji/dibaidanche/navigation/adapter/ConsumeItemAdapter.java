package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.LockEntity;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/25.
 */

public class ConsumeItemAdapter extends BaseRecyclerAdapter<ConsumeItemAdapter.ViewHolder, LockEntity> {

    public ConsumeItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(
                R.layout.adapter_navigation_wallet_item_consume, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LockEntity data = mData.get(position);
        holder.tvTime.setText(data.getEndTime());
        holder.tvMoney.setText(TextUtil.getMoneyByPenny(data.getConsumeMoney())+ "元");
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_money)
        TextView tvMoney;


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
