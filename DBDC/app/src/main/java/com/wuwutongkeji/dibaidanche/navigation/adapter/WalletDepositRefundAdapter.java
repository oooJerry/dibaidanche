package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/26.
 */

public class WalletDepositRefundAdapter extends BaseRecyclerAdapter<WalletDepositRefundAdapter.ViewHolder, String> {

    public WalletDepositRefundAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(
                R.layout.adapter_navigation_wallet_refund, parent, false));
    }

    private int index = -1;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String data = mData.get(position);

        holder.tvTitle.setText(data);

        holder.radioButton.setChecked(index == position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });

    }

    public String getReason(){
        if(index == -1){
            return null;
        }
        return mData.get(index);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.radioButton)
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
