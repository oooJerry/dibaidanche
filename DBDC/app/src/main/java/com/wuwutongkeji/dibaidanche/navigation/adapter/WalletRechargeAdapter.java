package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.common.util.TextUtil;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletRechargeAdapter extends BaseRecyclerAdapter
        <WalletRechargeAdapter.ViewHolder, Long> {

    public WalletRechargeAdapter(Context context) {
        super(context);
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

        holder.btnRecharge.setSelected(index == position);
        holder.btnRecharge.setText("充值" + TextUtil.getMoneyByPenny(data) + "元");

        holder.btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });
    }

    public long getSelectedData(){
        return mData.get(index);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.btn_recharge)
        Button btnRecharge;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
