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
 * Created by Mr.Bai on 2017/9/25.
 */

public class JourneyAdapter extends BaseRecyclerAdapter<JourneyAdapter.ViewHolder, LockEntity> {


    public JourneyAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(
                R.layout.adapter_navigation_journey, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LockEntity entity = mData.get(position);
        holder.tvTime.setText(entity.getEndTime());
        holder.tvBicycleNum.setText("车牌号: " + entity.getBicycleNum());
        holder.tvConsumeMoney.setText("￥"+ TextUtil.getMoneyByPenny(entity.getConsumeMoney()));
    }

    class ViewHolder extends BaseRecyclerViewHolder{

        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_bicycleNum)
        TextView tvBicycleNum;
        @BindView(R.id.tv_consumeMoney)
        TextView tvConsumeMoney;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
