package com.gnss.teachlearnpro.profile.detail.record.course;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.ui.LazyFragment;

public class CourseRecordFragment extends LazyFragment implements FragmentProvider {

    public static CourseRecordFragment newInstance() {

        Bundle args = new Bundle();

        CourseRecordFragment fragment = new CourseRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        MeLog.e("CourseRecordFragment create");
        return R.layout.vp_live;
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }

    @Override
    public void initData() {
        new CourseRecordLogic(this);
    }
}
