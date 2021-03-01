package com.gnss.teachlearnpro.group.detail.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

/**
 * 小组学习详情
 */
public class GroupStudyDetailFragment extends MeBaseFragment implements FragmentProvider {

    private GroupStudyDetailLogic mLogic;

    public static GroupStudyDetailFragment newInstance() {

        Bundle args = new Bundle();

        GroupStudyDetailFragment fragment = new GroupStudyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_study_detail;
    }

    @Override
    public void init() {
        mLogic = new GroupStudyDetailLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLogic.destroyWebView();
    }
}
