package com.wuwutongkeji.dibaidanche.navigation.contract.freecard;

import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.manager.PayManager;
import com.wuwutongkeji.dibaidanche.common.net.impl.DefaultNetSubscriber;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPayDialog;
import com.wuwutongkeji.dibaidanche.common.popup.FreeCardPublicityDialog;
import com.wuwutongkeji.dibaidanche.common.rx.RxUtils;
import com.wuwutongkeji.dibaidanche.common.util.DateUtil;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.DepositEntity;
import com.wuwutongkeji.dibaidanche.entity.FreeCardEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Mr.Bai on 17/11/19.
 */

public class FreeCardPresenter extends FreeCardContract.FreeCardBasePresenter
        implements FreeCardPayDialog.FreeCardListener {

    @Override
    protected void onAttach() {
        super.onAttach();

        onLoadData();
    }

    @Override
    public void onLoadData() {

        mNetDataManager.freeCard_list_all("0")
                .subscribe(new DefaultNetSubscriber<NetModel<List<FreeCardEntity>>>(mDialog) {
                    @Override
                    public void onCompleted(NetModel<List<FreeCardEntity>> netModel) {

                        if("NO_LOGIN".equals(netModel.getReturnCode())){
                            return;
                        }

                        Long remainTime = null;

                        if(null != netModel.getData()){
                            for(FreeCardEntity entity: netModel.getData()){
                                if(entity.getRemainTime() > 0 && "YEAR_CARD".equals(entity.getCardType())){
                                    if(null == remainTime){
                                        remainTime = new Long(0);
                                    }
                                    remainTime += entity.getRemainTime();
                                }
                            }
                        }

                        if(null == remainTime){
                            mDependView.onShowCardTitle("未使用");
                            mDependView.onShowSubmitTxt("充值年卡");
                        }else{
                            mDependView.onShowCardTitle("剩余" + DateUtil.long2Day(remainTime.longValue()) + "天");
                            mDependView.onShowSubmitTxt("年卡续费");
                        }
                    }
                });


        mNetDataManager.pay_depositAmount()
                .subscribe(new DefaultNetSubscriber<DepositEntity>(mDialog) {
                    @Override
                    public void onCompleted(DepositEntity entity) {
                        mDependView.onShowCardPrice("365天=" + (entity.getYearCard() / 100) + "元");
                    }
                });
    }

    @Override
    public void onPay(final PayManager.PayChannel payChannel) {

        mNetDataManager.pay_yearcard(String.valueOf(payChannel))
                .subscribe(new DefaultNetSubscriber<String>(mDialog) {
                    @Override
                    public void onCompleted(String s) {
                        mDependView.onPay(payChannel,s);
                    }
                });

    }

    @Override
    public PayManager.PayListener onPayListener() {
        return onPayListener;
    }

    PayManager.PayListener onPayListener = new PayManager.PayListener() {
        @Override
        public void onResult(boolean isSucess, String resultInfo) {
            if(isSucess){
                Observable.timer(1000 , TimeUnit.MILLISECONDS)
                        .compose(RxUtils.<Long>applyIOToMainThreadSchedulers())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                onLoadData();
                                EventBus.getDefault().post(new FreeCardEntity());
                            }
                        });

            }else{
                mDependView.showMsg("支付失败,请重试");
            }
        }
    };
}
