package com.wuwutongkeji.dibaidanche.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.activity.BrowserActivity;
import com.wuwutongkeji.dibaidanche.common.config.AppIntent;
import com.wuwutongkeji.dibaidanche.common.config.AppInterface;
import com.wuwutongkeji.dibaidanche.common.util.SharedPreferencesUtil;
import com.wuwutongkeji.dibaidanche.entity.ShareEntity;
import com.wuwutongkeji.dibaidanche.navigation.contract.invitefriend.InviteFriendContract;
import com.wuwutongkeji.dibaidanche.navigation.contract.invitefriend.InviteFriendPresenter;
import com.wuwutongkeji.dibaidanche.navigation.contract.journey.JourneyContract;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Bai on 17/9/26.
 */

public class InviteFriendActivity extends BaseToolbarActivity implements InviteFriendContract.InviteFriendBaseView{

    @BindView(R.id.btn_invitefriend)
    TextView btnInvitefriend;
    @BindView(R.id.btn_wechat)
    TextView btnWechat;
    @BindView(R.id.btn_moments)
    TextView btnMoments;
    @BindView(R.id.btn_browser)
    TextView btnBrowser;


    InviteFriendContract.InviteFriendBasePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_invitefriend;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("邀请好友");

        btnInvitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AppIntent.getBrowserActivity(mContext);
                intent.putExtra(BrowserActivity.KEY_TITLE,"详细规则");
                intent.putExtra(BrowserActivity.KEY_DATA, AppInterface.INVITE_USER);
                startActivity(intent);
            }
        });

        final String shareUrl = AppInterface.ADDRESS
                            + AppInterface.SHARE_CODE + "?userId="
                            + SharedPreferencesUtil.getUser().getId();

        final UMWeb web = new UMWeb(shareUrl);
        web.setTitle("邀请好友,免费骑车");
        web.setThumb(new UMImage(this, R.mipmap.ic_launcher));
        web.setDescription("喊小伙伴来骑的拜单车,免费用车券拿到手软");

        btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareAction(InviteFriendActivity.this)
                        .withMedia(web)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .share();
            }
        });

        btnMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareAction(InviteFriendActivity.this)
                        .withMedia(web)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .share();
            }
        });

        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppIntent.getSystemBrowserActivity(shareUrl));
            }
        });

    }

    @Override
    protected void initData() {
        mPresenter = newPresenter(new InviteFriendPresenter(),this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    @Override
    public void onLoadData(ShareEntity entity) {
        PlatformConfig.setWeixin(entity.getAppId(), entity.getAppSecret());
    }

    @Override
    public InviteFriendContract.InviteFriendBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBusinessFinish(Serializable serializable) {

    }
}