package com.gnss.teachlearnpro.live;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ecommerce.meui.tab.top.MeTabTop;
import com.ecommerce.meui.tab.top.MeTabTopInfo;
import com.ecommerce.meui.tab.top.MeTabTopLayout;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.live.adapter.ViewPagerFragmentStateAdapter;
import com.gnss.teachlearnpro.live.liveback.LiveBackFragment;
import com.gnss.teachlearnpro.live.liveplan.LivePlanFragment;

import java.util.ArrayList;
import java.util.List;

public class LiveLogic {
    private FragmentProvider mFragmentProvider;
    private MeTabTopLayout mTabToplayout;
    private SuperTextView mStv;
    private String[] mTabs = {StringUtils.getString(R.string.live_plan), StringUtils.getString(R.string.live_back)};
    private int mCurItemIndex;
    private List<MeTabTopInfo<?>> mInfoList;
    private ViewPager2 mVp;

    public LiveLogic(FragmentProvider fragmentProvider) {
        this.mFragmentProvider = fragmentProvider;
        initView();
        initTap();
    }

    private void initView() {
        mTabToplayout = mFragmentProvider.getMineView().findViewById(R.id.mtl_tab_top);
        mTabToplayout.setBackgroundColor(Color.parseColor("#FFF5F9FC"));
        mStv = mFragmentProvider.getMineView().findViewById(R.id.stv_common_title);
        mStv.setCenterString(StringUtils.getString(R.string.live));
    }

    private void initTap() {
        int defaultColor = mFragmentProvider.getResources().getColor(R.color.tabTopDefaultColor);
        int tintColor = mFragmentProvider.getResources().getColor(R.color.black);

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
        tabFirst.getTvName().setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        tabSecond.getTvName().setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(4));
        params.leftMargin = SizeUtils.dp2px(70);
        params.rightMargin = SizeUtils.dp2px(70);
        tabFirst.getIndicator().setLayoutParams(params);
        tabSecond.getIndicator().setLayoutParams(params);
    }

    private void initViewPage() {
        List<Fragment> fragments = new ArrayList<>();
        mVp = mFragmentProvider.getMineView().findViewById(R.id.vp_fragment_live);

        fragments.add(LivePlanFragment.newInstance());
        fragments.add(LiveBackFragment.newInstance());
        ViewPagerFragmentStateAdapter adapter = new ViewPagerFragmentStateAdapter(mFragmentProvider.getParentFragmentManager()
                , mFragmentProvider.getLifecycle(), fragments);
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
}
