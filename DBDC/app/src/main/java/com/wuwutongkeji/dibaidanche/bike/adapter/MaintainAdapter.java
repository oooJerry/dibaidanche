package com.wuwutongkeji.dibaidanche.bike.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;
import com.wuwutongkeji.dibaidanche.entity.OptionTypeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/24.
 */

public class MaintainAdapter extends BaseRecyclerAdapter<MaintainAdapter.ViewHolder, OptionTypeEntity> {

    int type;

    List<OptionTypeEntity> mSelectedOptions = new ArrayList<>();


    public MaintainAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.adapter_bike_maintain, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final OptionTypeEntity entity = mData.get(position);

        holder.btnOption.setText(entity.getField());

        final boolean contains = mSelectedOptions.contains(entity);

        holder.btnOption.setSelected(contains);

        holder.btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contains){
                    type -= entity.getCode();
                    mSelectedOptions.remove(entity);
                }else {
                    type += entity.getCode();
                    mSelectedOptions.add(entity);
                }
                onMaintainListener.onItem(type);
                notifyItemChanged(position);
            }
        });

    }

    public int getType(){
        return type;
    }


    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.btn_option)
        Button btnOption;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    private MaintainListener onMaintainListener;
    public void setOnMaintainListener(MaintainListener onMaintainListener){
        this.onMaintainListener = onMaintainListener;
    }
    public interface MaintainListener{
        void onItem(int type);
    }
}
