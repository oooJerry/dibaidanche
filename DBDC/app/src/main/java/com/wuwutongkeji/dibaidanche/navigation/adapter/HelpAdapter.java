package com.wuwutongkeji.dibaidanche.navigation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.entity.HelpEntity;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/9/26.
 */

public class HelpAdapter extends BaseRecyclerAdapter<HelpAdapter.ViewHolder, HelpEntity> {

    public HelpAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.
                inflate(R.layout.adapter_navigation_help, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HelpEntity entity = mData.get(position);
        holder.tvTitle.setText(entity.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != onHelpListener){
                    onHelpListener.onItem(entity);
                }
            }
        });
    }

    private HelpListener onHelpListener;

    public void setOnHelpListener( HelpListener onHelpListener){
        this.onHelpListener = onHelpListener;
    }
    public interface HelpListener{
        void onItem(HelpEntity entity);
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
