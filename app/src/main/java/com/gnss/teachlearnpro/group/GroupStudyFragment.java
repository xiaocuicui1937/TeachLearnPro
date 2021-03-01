package com.gnss.teachlearnpro.group;

import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class GroupStudyFragment extends MeBaseFragment implements FragmentProvider {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_study;
    }

    @Override
    public void init() {
        super.init();
        new GroupStudyLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
