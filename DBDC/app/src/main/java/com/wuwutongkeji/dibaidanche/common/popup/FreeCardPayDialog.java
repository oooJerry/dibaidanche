package com.wuwutongkeji.dibaidanche.common.popup;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 2017/11/20.
 */

public class FreeCardPayDialog extends BaseDialog {

    @BindView(R.id.aliPay_radio)
    RadioButton aliPayRadio;
    @BindView(R.id.btn_alipay)
    LinearLayout btnAlipay;
    @BindView(R.id.wechat_radio)
    RadioButton wechatRadio;
    @BindView(R.id.btn_wechat)
    LinearLayout btnWechat;
    @BindView(R.id.btn_moreType)
    LinearLayout btnMoreType;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;

    @Override
    public void onStart() {
        setOutCancel(true);
        aliPayRadio.setChecked(true);
        super.onStart();
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_freecard_pay;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        btnAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aliPayRadio.setChecked(true);
            }
        });

        btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatRadio.setChecked(true);
            }
        });


        btnMoreType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMoreType.setVisibility(View.GONE);
                btnWechat.setVisibility(View.VISIBLE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFreeCardListener.onPay(aliPayRadio.isChecked() ?
                        PayManager.PayChannel.ALIPAY : PayManager.PayChannel.WECHATPAY);
                dismiss();
            }
        });

        mDecoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void initDatas() {

    }

    private FreeCardListener onFreeCardListener;
    public void setOnFreeCardListener(FreeCardListener onFreeCardListener){
        this.onFreeCardListener = onFreeCardListener;
    }
    public interface FreeCardListener{
        void onPay(PayManager.PayChannel channel);
    }
}
