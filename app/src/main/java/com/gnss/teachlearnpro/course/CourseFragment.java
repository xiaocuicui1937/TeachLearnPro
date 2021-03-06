package com.gnss.teachlearnpro.course;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

/**
 * 系列课程模块
 */
public class CourseFragment extends MeBaseFragment implements FragmentProvider {

    public static CourseFragment newInstance() {

        Bundle args = new Bundle();

        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    public void init() {
        super.init();
        new CourseLogic(this);
    }


    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
