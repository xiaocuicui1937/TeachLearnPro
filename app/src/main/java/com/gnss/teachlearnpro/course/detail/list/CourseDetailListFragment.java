package com.gnss.teachlearnpro.course.detail.list;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class CourseDetailListFragment extends MeBaseFragment implements FragmentProvider {

    private CourseDetailListLogic mLogic;

    public static CourseDetailListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(Contact.ID,id);
        CourseDetailListFragment fragment = new CourseDetailListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course_detail_list;
    }



    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }

    @Override
    public void init() {
        mLogic = new CourseDetailListLogic(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLogic.destroyWebView();
    }
}
