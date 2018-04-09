package com.wuwutongkeji.dibaidanche.common.config;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wuwutongkeji.dibaidanche.bike.BikeFeedbackActivity;
import com.wuwutongkeji.dibaidanche.bike.BikeMaintainActivity;
import com.wuwutongkeji.dibaidanche.bike.BikeUsingFinishActivity;
import com.wuwutongkeji.dibaidanche.bike.InputCodeUnlockActivity;
import com.wuwutongkeji.dibaidanche.bike.ScanCodeUnlockActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.activity.ChoosePicActivity;
import com.wuwutongkeji.dibaidanche.launch.AppealActivity;
import com.wuwutongkeji.dibaidanche.launch.ApproveActivity;
import com.wuwutongkeji.dibaidanche.launch.CompleteActivity;
import com.wuwutongkeji.dibaidanche.launch.DepositActivity;
import com.wuwutongkeji.dibaidanche.launch.LoginActivity;
import com.wuwutongkeji.dibaidanche.launch.MainActivity;
import com.wuwutongkeji.dibaidanche.navigation.AboutUsActivity;
import com.wuwutongkeji.dibaidanche.navigation.CreditActivity;
import com.wuwutongkeji.dibaidanche.navigation.FreeCardActivity;
import com.wuwutongkeji.dibaidanche.navigation.HelpActivity;
import com.wuwutongkeji.dibaidanche.navigation.InviteFriendActivity;
import com.wuwutongkeji.dibaidanche.navigation.JourneyActivity;
import com.wuwutongkeji.dibaidanche.navigation.PersonalInfoActivity;
import com.wuwutongkeji.dibaidanche.navigation.PersonalNameActivity;
import com.wuwutongkeji.dibaidanche.navigation.WalletActivity;
import com.wuwutongkeji.dibaidanche.navigation.WalletCouponActivity;
import com.wuwutongkeji.dibaidanche.navigation.WalletDepositActivity;
import com.wuwutongkeji.dibaidanche.navigation.WalletDepositRefundActivity;
import com.wuwutongkeji.dibaidanche.navigation.WalletDetailActivity;
import com.wuwutongkeji.dibaidanche.navigation.WalletRechargeActivity;

/**
 * Created by Mr.Bai on 2017/9/12.
 */

public class AppIntent {

    // 登录
    public static Intent getLoginActivity(Context mContext){
        return new Intent(mContext, LoginActivity.class);
    }
    // 首页
    public static Intent getMainActivity(Context mContext){
        return new Intent(mContext,MainActivity.class);
    }
    // 押金
    public static Intent getDepositActivity(Context mContext){
        return new Intent(mContext,DepositActivity.class);
    }

    // 实名认证
    public static Intent getApproveActivity(Context mContext){
        return new Intent(mContext,ApproveActivity.class);
    }

    // 完成
    public static Intent getCompleteActivity(Context mContext){
        return new Intent(mContext,CompleteActivity.class);
    }

    // 申诉
    public static Intent getAppealActivity(Context mContext){
        return new Intent(mContext,AppealActivity.class);
    }
    // 扫码开锁
    public static Intent getScanCodeUnlockActivity(Context mContext){
        return new Intent(mContext,ScanCodeUnlockActivity.class);
    }

    // 输入验证码开锁
    public static Intent getInputCodeUnlockActivity(Context mContext){
        return new Intent(mContext,InputCodeUnlockActivity.class);
    }
    // 结束用车
    public static Intent getBikeUsingFinishActivity(Context mContext){
        return new Intent(mContext,BikeUsingFinishActivity.class);
    }

    // 报修
    public static Intent getBikeMaintainActivity(Context mContext){
        return new Intent(mContext,BikeMaintainActivity.class);
    }

    // 反馈
    public static Intent getBikeFeedbackActivity(Context mContext){
        return new Intent(mContext,BikeFeedbackActivity.class);
    }

    // 个人信息
    public static Intent getPersonalInfoActivity(Context mContext){
        return new Intent(mContext,PersonalInfoActivity.class);
    }

    // 修改个人昵称
    public static Intent getPersonalNameActivity(Context mContext){
        return new Intent(mContext,PersonalNameActivity.class);
    }

    // 我的行程
    public static Intent getJourneyActivity(Context mContext){
        return new Intent(mContext,JourneyActivity.class);
    }

    // 我的钱包
    public static Intent getWalletActivity(Context mContext){
        return new Intent(mContext,WalletActivity.class);
    }
    // 我的钱包明细
    public static Intent getWalletDetailActivity(Context mContext){
        return new Intent(mContext,WalletDetailActivity.class);
    }
    // 年卡
    public static Intent getFreeCardActivity(Context mContext){
        return new Intent(mContext,FreeCardActivity.class);
    }
    // 我的钱包优惠券
    public static Intent getWalletCouponActivity(Context mContext){
        return new Intent(mContext,WalletCouponActivity.class);
    }
    // 我的钱包余额
    public static Intent getWalletDepositActivity(Context mContext){
        return new Intent(mContext,WalletDepositActivity.class);
    }

    // 我的钱包余额退还
    public static Intent getWalletDepositRefundActivity(Context mContext){
        return new Intent(mContext,WalletDepositRefundActivity.class);
    }

    // 我的钱包余额充值
    public static Intent getWalletRechargeActivity(Context mContext){
        return new Intent(mContext,WalletRechargeActivity.class);
    }

    // 我的信用积分
    public static Intent getCreditActivity(Context mContext){
        return new Intent(mContext,CreditActivity.class);
    }

    // 邀请好友
    public static Intent getInviteFriendActivity(Context mContext){
        return new Intent(mContext,InviteFriendActivity.class);
    }

    // 使用指南
    public static Intent getHelpActivity(Context mContext){
        return new Intent(mContext,HelpActivity.class);
    }

    // 关于我们
    public static Intent getAboutUsActivity(Context mContext){
        return new Intent(mContext,AboutUsActivity.class);
    }

    // 图片选择
    public static Intent getChoosePicActivity(Context mContext){
        return new Intent(mContext, ChoosePicActivity.class);
    }

    // 浏览器
    public static Intent getBrowserActivity(Context mContext){
        return new Intent(mContext, BrowserActivity.class);
    }

    // 拨打电话
    public static Intent getTelActivity(String tel){
        Uri uri = Uri.parse("tel:" + tel);
        return new Intent(Intent.ACTION_DIAL, uri);
    }

    //系统浏览器
    public static Intent getSystemBrowserActivity(String url){
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        return intent;
    }
}
