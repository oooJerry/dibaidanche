package com.wuwutongkeji.dibaidanche.navigation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuwutongkeji.dibaidanche.R;
import com.wuwutongkeji.dibaidanche.base.BaseToolbarActivity;
import com.wuwutongkeji.dibaidanche.common.util.DeviceUtil;

import java.lang.reflect.Field;

import butterknife.BindView;


/**
 * Created by Mr.Bai on 17/9/25.
 */

public class WalletDetailActivity extends BaseToolbarActivity {

    @BindView(R.id.tableLayout)
    TabLayout tableLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_wallet_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setTitle("明细");
        WalletDetailAdapter mAdapter = new WalletDetailAdapter(getSupportFragmentManager());
        showTabTextAdapteIndicator(tableLayout);
        viewpager.setAdapter(mAdapter);
        tableLayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(mAdapter.mFragments.length);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        super.initToolbar(savedInstanceState);
        useArrowBackIcon();
    }

    public void showTabTextAdapteIndicator(final TabLayout tab) {
        tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tab.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Class<?> tabLayout = tab.getClass();
                Field tabStrip = null;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                tabStrip.setAccessible(true);
                LinearLayout ll_tab = null;
                try {
                    ll_tab = (LinearLayout) tabStrip.get(tab);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                int maxLen = 0;
                int maxTextSize = 0;
                int tabCount = ll_tab.getChildCount();
                for (int i = 0; i < tabCount; i++) {
                    View child = ll_tab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    if (child instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) child;
                        for (int j = 0; j < ll_tab.getChildCount(); j++) {
                            if (viewGroup.getChildAt(j) instanceof TextView) {
                                TextView tabTextView = (TextView) viewGroup.getChildAt(j);
                                int length = tabTextView.getText().length();
                                maxTextSize = (int) tabTextView.getTextSize() > maxTextSize ? (int) tabTextView.getTextSize() : maxTextSize;
                                maxLen = length > maxLen ? length : maxLen;
                            }
                        }

                    }

                    int margin = (tab.getWidth() / tabCount - (maxTextSize + DeviceUtil.dp2px(2)) * maxLen) / 2 - DeviceUtil.dp2px(2);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = margin;
                    params.rightMargin = margin;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        });

    }

    class WalletDetailAdapter extends FragmentPagerAdapter {

        String[] mTitle = {"消费明细","充值明细"};

        Fragment[] mFragments = {new ConsumeItemFragment(),new RechargeItemFragment()};

        public WalletDetailAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    };
}
