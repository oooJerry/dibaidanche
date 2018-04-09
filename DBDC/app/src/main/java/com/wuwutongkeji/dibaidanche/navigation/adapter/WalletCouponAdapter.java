package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;
import com.wuwutongkeji.dibaidanche.entity.WalletCouponEntity;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletCouponAdapter extends BaseRecyclerAdapter<WalletCouponAdapter.ViewHolder, WalletCouponEntity.DataEntity> {

    public WalletCouponAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(
                R.layout.adapter_navigation_wallet_coupon, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WalletCouponEntity.DataEntity data = mData.get(position);
        holder.tvTime.setText("有效期至 " + data.getExpireTime());
        holder.tvMoney.setText(TextUtil.getMoneyByPenny(data.getDeductionAmount()));
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
