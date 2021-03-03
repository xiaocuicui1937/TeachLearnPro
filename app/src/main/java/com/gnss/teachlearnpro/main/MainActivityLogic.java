package com.gnss.teachlearnpro.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.ecommerce.common.tab.MeFragmentTabView;
import com.ecommerce.common.tab.MeTabViewAdapter;
import com.ecommerce.meui.tab.bottom.MeTabBottomInfo;
import com.ecommerce.meui.tab.bottom.MeTabBottomLayout;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.course.CourseFragment;
import com.gnss.teachlearnpro.group.GroupStudyFragment;
import com.gnss.teachlearnpro.group.GroupStudyViewModel;
import com.gnss.teachlearnpro.group.entry.GroupStudyEntryFragment;
import com.gnss.teachlearnpro.live.LiveFragment;
import com.gnss.teachlearnpro.main.home.HomePageFragment;
import com.gnss.teachlearnpro.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 将MainActivity内置到这里，使用MainActivity更加清爽
 */
public class MainActivityLogic {
    private MeFragmentTabView mFragmentTabView;

    private List<MeTabBottomInfo<?>> mInfoList;
    private ActivityProvider mActivityProvider;
    private static final String SAVE_CURRENT_ID = "save_current_id";
    private int mCurItemIndex;
    private MeTabBottomLayout mBottomLayout;


    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.mActivityProvider = activityProvider;
        //fix 不保留活动导致的Fragment重叠的问题
        if (savedInstanceState != null) {
            mCurItemIndex = savedInstanceState.getInt(SAVE_CURRENT_ID);
        }
        requestIsEntry();
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVE_CURRENT_ID, mCurItemIndex);
    }

    public MeFragmentTabView getFragmentTabView() {
        return mFragmentTabView;
    }

    public MeTabBottomLayout getBottomLayout() {
        return mBottomLayout;
    }

    public List<MeTabBottomInfo<?>> getInfoList() {
        return mInfoList;
    }

    private void initTabBottom(boolean isEntry) {
        mBottomLayout = mActivityProvider.findViewById(R.id.mtbl_activity_main);
        mBottomLayout.setTabAlpha(1);
        mInfoList = new ArrayList<>();
        int defaultColor = mActivityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = mActivityProvider.getResources().getColor(R.color.tabBottomTintColor);
        Bitmap homeDefault = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_home_default);
        Bitmap homeTint = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_home_tint);
        MeTabBottomInfo<Integer> home = new MeTabBottomInfo<>(
                mActivityProvider.getString(R.string.home),
                homeDefault,
                homeTint,
                defaultColor,
                tintColor
        );
        home.fragment = HomePageFragment.class;
        Bitmap liveDefault = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_live_default);
        Bitmap liveTint = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_live_tint);
        MeTabBottomInfo<Integer> categray = new MeTabBottomInfo<>(
                mActivityProvider.getString(R.string.live),
                liveDefault,
                liveTint,
                defaultColor,
                tintColor
        );
        categray.fragment = LiveFragment.class;
        Bitmap courseDefault = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_course_default);
        Bitmap courseTint = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_course_tint);
        MeTabBottomInfo<Integer> course = new MeTabBottomInfo<>(
                mActivityProvider.getString(R.string.course),
                courseDefault,
                courseTint,
                defaultColor,
                tintColor
        );
        course.fragment = CourseFragment.class;
        Bitmap groupDefault = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_group_default);
        Bitmap groupTint = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_group_tint);
        MeTabBottomInfo<Integer> recommend = new MeTabBottomInfo<>(
                mActivityProvider.getString(R.string.group_lean),
                groupDefault,
                groupTint,
                defaultColor,
                tintColor
        );
        recommend.fragment = isEntry ? GroupStudyFragment.class : GroupStudyEntryFragment.class;
        Bitmap profileDefault = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_profile_defalut);
        Bitmap profileTint = BitmapFactory.decodeResource(mActivityProvider.getResources(), R.drawable.ic_profile_tint);
        MeTabBottomInfo<Integer> profile = new MeTabBottomInfo<>(
                mActivityProvider.getString(R.string.profile),
                profileDefault,
                profileTint,
                defaultColor,
                tintColor
        );
        profile.fragment = ProfileFragment.class;

        mInfoList.add(home);
        mInfoList.add(categray);
        mInfoList.add(course);
        mInfoList.add(recommend);
        mInfoList.add(profile);
        mBottomLayout.inflateInfo(mInfoList);

        initTabFragmentView();
        mBottomLayout.addTabSelectedChangeListener((index, preInfo, nextInfo) -> {
                    mFragmentTabView.setCurrentItem(index);
                    mCurItemIndex = index;
                    if (mCurItemIndex==2){
                        CacheMemoryUtils.getInstance().put(Contact.IS_ACTIVITY,false);
                        CacheMemoryUtils.getInstance().put(Contact.ID, 0);
                    }
                }
        );
        mBottomLayout.defaultSelected(mInfoList.get(mCurItemIndex));
    }

    private void initTabFragmentView() {
        MeTabViewAdapter adapter = new MeTabViewAdapter(mActivityProvider.getSupportFragmentManager(), mInfoList);
        mFragmentTabView = mActivityProvider.findViewById(R.id.mftv_activity_main);
        mFragmentTabView.setAdapter(adapter);
    }

    /**
     * 获取小组是否已经报名信息
     */
    private void requestIsEntry() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        GroupStudyViewModel model = new ViewModelProvider(act).get(GroupStudyViewModel.class);
        model.isGroupEntry();
        model.getGroupStudyEntryOrNot().observe(act, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEntry) {
                initTabBottom(isEntry);
            }
        });
    }

}
