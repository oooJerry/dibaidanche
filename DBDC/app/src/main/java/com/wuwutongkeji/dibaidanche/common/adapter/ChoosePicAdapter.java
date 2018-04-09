package com.wuwutongkeji.dibaidanche.common.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerAdapter;
import com.wuwutongkeji.dibaidanche.base.BaseRecyclerViewHolder;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/9/24.
 */

public class ChoosePicAdapter extends BaseRecyclerAdapter<BaseRecyclerViewHolder, Uri> {

    private int max_num;

    public ChoosePicAdapter(Context context,int max_num) {
        super(context);
        this.max_num = max_num;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position + 1) <= super.getItemCount() ? 0 : 1;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(mLayoutInflater.inflate(
                    R.layout.adapter_common_adapter_choose_item, parent, false));
        } else {
            return new AddViewHolder(mLayoutInflater.inflate(
                    R.layout.adapter_common_adapter_choose_add, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if(holder instanceof AddViewHolder){

            AddViewHolder vh = (AddViewHolder) holder;
            vh.iconImg.setVisibility(
                    super.getItemCount() >= max_num ? View.GONE : View.VISIBLE);


            vh.iconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null != onChoosePicListener){
                        onChoosePicListener.onAddPic();
                    }
                }
            });
        }

        if(holder instanceof ViewHolder){

            ChoosePicAdapter.ViewHolder vh = (ViewHolder) holder;

            final Uri data = mData.get(position);
            vh.iconImg.setImageURI(data);

            vh.iconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(data);
                }
            });
        }
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.icon_img)
        SimpleDraweeView iconImg;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class AddViewHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.icon_img)
        ImageView iconImg;
        public AddViewHolder(View itemView) {
            super(itemView);
        }
    }

    private ChoosePicListener onChoosePicListener;
    public void setOnChoosePicListener(ChoosePicListener onChoosePicListener){
        this.onChoosePicListener = onChoosePicListener;
    }
    public interface ChoosePicListener{

        void onAddPic();

    }
}
