package com.wuwutongkeji.dibaidanche.common.popup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseDialog;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;

import butterknife.BindView;

/**
 * Created by Mr.Bai on 17/10/16.
 */

public class DepositRefundDialog extends BaseDialog {

    @BindView(R.id.btn_Refund)
    Button btnRefund;
    @BindView(R.id.btn_NoRefund)
    Button btnNoRefund;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_wallet_depositrefund;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getWalletDepositRefundActivity(mContext));
                dismiss();
                getActivity().finish();
            }
        });

        btnNoRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void initDatas() {

    }
}