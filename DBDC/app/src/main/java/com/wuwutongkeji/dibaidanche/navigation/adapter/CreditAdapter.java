package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.entity.CreditEntity;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class CreditAdapter extends BaseRecyclerAdapter
        <CreditAdapter.ViewHolder, CreditEntity.DataEntity> {

    public CreditAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(
                R.layout.adapter_navigation_credit, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CreditEntity.DataEntity entity = mData.get(position);

        holder.tvCreditDesc.setText(entity.getCreditDesc());
        holder.tvTime.setText(entity.getCreateTime());
        holder.tvScore.setText("信用分+" + entity.getScore());
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_creditDesc)
        TextView tvCreditDesc;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_score)
        TextView tvScore;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
