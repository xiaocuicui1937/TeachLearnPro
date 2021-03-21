package com.gnss.teachlearnpro.live.liveplan.detail;

import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.UiMessageUtils;
import com.ecommerce.meui.tab.top.MeTabTop;
import com.ecommerce.meui.tab.top.MeTabTopInfo;
import com.ecommerce.meui.tab.top.MeTabTopLayout;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.live.adapter.ViewPagerFragmentStateAdapter;
import com.gnss.teachlearnpro.live.liveplan.detail.liveInteract.LiveInteractInnerFragment;
import com.gnss.teachlearnpro.live.liveplan.detail.livedetail.LiveInnerDetailFragment;
import com.gnss.teachlearnpro.main.live.detail.fragment.LivePlayerManager;

import java.util.ArrayList;
import java.util.List;

public class PlanDetailALogic extends BaseLogic {
    private ActivityProvider mProvider;
    private MeTabTopLayout mTabToplayout;
    //    private SuperTextView mStv;
    private String[] mTabs = {StringUtils.getString(R.string.detail), StringUtils.getString(R.string.interact)};
    private int mCurItemIndex;
    private List<MeTabTopInfo<?>> mInfoList;
    private ViewPager2 mVp;
    private LivePlayerManager mLivePlayerManager;

    public PlanDetailALogic(ActivityProvider provider) {
        mProvider = provider;
        initView();
        initTap();
    }

    private void initView() {
        mTabToplayout = mProvider.findViewById(R.id.mtl_tab_top_plan_detail);
        mLivePlayerManager = new LivePlayerManager(mProvider.findViewById(R.id.live_top_bg));
        setTitle(CacheMemoryUtils.getInstance().get(Contact.TITLE));
        //播放直播
        UiMessageUtils.getInstance().addListener(Contact.LIVE_UPDATE_URL, localMessage -> {
            String liveUrl = String.valueOf(localMessage.getObject());
            mLivePlayerManager.playLive(liveUrl);
        });
    }

    private void initTap() {
        int defaultColor = mProvider.getResources().getColor(R.color.tabTopDefaultColor);
        int tintColor = mProvider.getResources().getColor(R.color.black);

        mInfoList = new ArrayList<>();
        for (String param : mTabs) {
            MeTabTopInfo<?> tabTop = new MeTabTopInfo<>(param, defaultColor, tintColor);
            mInfoList.add(tabTop);
        }
        mTabToplayout.inflateInfo(mInfoList);
        initViewPage();
        mTabToplayout.addTabSelectedChangeListener((index, preInfo, nextInfo) ->
        {
            mVp.setCurrentItem(index);
            mCurItemIndex = index;
        });

        mTabToplayout.defaultSelected(mInfoList.get(mCurItemIndex));
        MeTabTop tabFirst = mTabToplayout.findTab(mInfoList.get(0));
        MeTabTop tabSecond = mTabToplayout.findTab(mInfoList.get(1));
        tabFirst.getTvName().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tabSecond.getTvName().setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(4));
        params.leftMargin = SizeUtils.dp2px(80);
        params.rightMargin = SizeUtils.dp2px(80);
        tabFirst.getIndicator().setLayoutParams(params);
        tabSecond.getIndicator().setLayoutParams(params);
    }

    private void initViewPage() {
        List<Fragment> fragments = new ArrayList<>();
        mVp = mProvider.findViewById(R.id.vp_fragment_plan_detail);

        fragments.add(LiveInnerDetailFragment.newInstance());
        fragments.add(LiveInteractInnerFragment.newInstance());
        ViewPagerFragmentStateAdapter adapter = new ViewPagerFragmentStateAdapter(mProvider.getSupportFragmentManager()
                , mProvider.getLifecycle(), fragments);
        mVp.setAdapter(adapter);
        //注册回调页面变化
        mVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabToplayout.defaultSelected(mInfoList.get(position));
                mCurItemIndex = position;
            }
        });
    }

    public void resumeLive() {
        // 继续
        mLivePlayerManager.resumeLive();
    }

    public void pauseLive() {
        // 暂停
        mLivePlayerManager.pauseLive();
    }

    /**
     * 结束直播
     */
    public void stopLive() {
        mLivePlayerManager.stopLive();
    }
}
