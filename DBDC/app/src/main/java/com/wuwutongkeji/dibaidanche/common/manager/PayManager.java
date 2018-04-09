package com.wuwutongkeji.dibaidanche.common.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.common.util.GsonUtil;
import com.wuwutongkeji.dibaidanche.entity.WeChatPayEntity;
import com.wuwutongkeji.dibaidanche.wxapi.WXPayEntryActivity;

import java.util.Map;

/**
 * Created by Mr.Bai on 17/9/15.
 */

public class PayManager {

    public static void aliPay(final String orderInfo, final Activity mContext, final PayListener payListener){

        if(null == payListener){
            throw new RuntimeException("PayListener 不能为空");
        }
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Map<String, String> map = (Map<String, String>) msg.obj;

                String resultInfo = map.get("result");// 同步返回需要验证的信息
                String resultStatus = map.get("resultStatus");

                payListener.onResult(TextUtils.equals(resultStatus, "9000"),resultInfo);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {

                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public static void weChatPay(Context mContext,String orderInfo, final PayListener payListener){


        WeChatPayEntity entity =
                (WeChatPayEntity) GsonUtil.xml2Bean(orderInfo,WeChatPayEntity.class);
        IWXAPI api = WXAPIFactory.createWXAPI(mContext,entity.getXml().getAppid());
        api.registerApp(entity.getXml().getAppid());

        PayReq req = new PayReq();
        req.appId			= entity.getXml().getAppid();
        req.partnerId		= entity.getXml().getPartnerid();
        req.prepayId		= entity.getXml().getPrepayid();
        req.packageValue	= entity.getXml().getPackageX();
        req.timeStamp		= entity.getXml().getTimestamp();
        req.nonceStr        = entity.getXml().getNoncestr();
        req.sign			= entity.getXml().getSign();

        WXPayEntryActivity.APPID = req.appId;
        WXPayEntryActivity.mPayListener = payListener;
        api.sendReq(req);
    }

    public interface PayListener{

        void onResult(boolean isSucess,String resultInfo);

    }


    public enum PayChannel{

        ALIPAY,

        WECHATPAY
    }
}
