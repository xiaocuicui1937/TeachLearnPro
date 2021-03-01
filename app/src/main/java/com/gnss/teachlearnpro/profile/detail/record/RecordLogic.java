package com.gnss.teachlearnpro.profile.detail.record;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ecommerce.meui.tab.top.MeTabTop;
import com.ecommerce.meui.tab.top.MeTabTopInfo;
import com.ecommerce.meui.tab.top.MeTabTopLayout;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.live.adapter.ViewPagerFragmentStateAdapter;
import com.gnss.teachlearnpro.profile.detail.record.course.CourseRecordFragment;
import com.gnss.teachlearnpro.profile.detail.record.live.LiveRecordFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordLogic extends BaseLogic {

    private FragmentProvider mFrovider;
    private MeTabTopLayout mTabToplayout;
    private String[] mTabs = {StringUtils.getString(R.string.live_record), StringUtils.getString(R.string.course_record)};
    private int mCurItemIndex;
    private List<MeTabTopInfo<?>> mInfoList;
    private ViewPager2 mVp;

    public RecordLogic(FragmentProvider provider) {
        this.mFrovider = provider;
        setTitle(provider,"学习记录",(AppCompatActivity) provider.getActivity());
        initTap();
    }

    private void initTap() {
        mTabToplayout = mFrovider.getMineView().findViewById(R.id.mtl_tab_top);
        int defaultColor = mFrovider.getResources().getColor(R.color.tabTopDefaultColor);
        int tintColor = mFrovider.getResources().getColor(R.color.black);

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


        tabSecond.getTvName().setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(4));
        params.leftMargin = SizeUtils.dp2px(70);
        params.rightMargin = SizeUtils.dp2px(70);
        tabFirst.getIndicator().setLayoutParams(params);
        tabSecond.getIndicator().setLayoutParams(params);
    }

    @SuppressLint("WrongConstant")
    private void initViewPage() {
        List<Fragment> fragments = new ArrayList<>();
        mVp = mFrovider.getMineView().findViewById(R.id.vp_fragment_live);

        fragments.add(CourseRecordFragment.newInstance());
        fragments.add(LiveRecordFragment.newInstance());

        ViewPagerFragmentStateAdapter adapter = new ViewPagerFragmentStateAdapter(mFrovider.getParentFragmentManager()
                , mFrovider.getLifecycle(), fragments);
        mVp.setAdapter(adapter);
        //禁止加载左右Fragment实现懒加载
//        mVp.setOffscreenPageLimit(0);
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
