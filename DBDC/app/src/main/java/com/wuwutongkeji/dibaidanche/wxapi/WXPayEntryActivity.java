package com.wuwutongkeji.dibaidanche.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;

public class WXPayEntryActivity extends WXCallbackActivity{
	
	private static String TAG = "WXPayEntryActivity";

	public volatile static String APPID;

	public volatile static PayManager.PayListener mPayListener;

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		super.onReq(req);
	}

	@Override
	public void onResp(BaseResp resp) {
		super.onResp(resp);
		boolean isSuccess = resp.errCode == 0;
		mPayListener.onResult(isSuccess,isSuccess ? "支付成功" : "支付失败");
		finish();
	}
}