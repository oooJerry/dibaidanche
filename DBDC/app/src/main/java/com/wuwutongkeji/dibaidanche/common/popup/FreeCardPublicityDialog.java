package com.wuwutongkeji.dibaidanche.common.popup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public class FreeCardPublicityDialog extends BaseDialog {

    @BindView(R.id.btn_freeCard)
    TextView btnFreeCard;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.simpleDrawee)
    SimpleDraweeView simpleDrawee;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_freecard_publicity;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        setCancelable(false);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String imgUrl = AppInterface.DEFAULT_ADDRESS + "images/shouye.png?" + System.currentTimeMillis() + "_" + new Random().nextInt(10);
        simpleDrawee.setImageURI(imgUrl);
        btnFreeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AppIntent.getFreeCardActivity(mContext);
                startActivity(intent);
                dismiss();
            }
        });
    }

    @Override
    public void initDatas() {

    }
}
