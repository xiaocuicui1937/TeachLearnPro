package com.gnss.teachlearnpro.group.entry;

import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class GroupStudyEntryFragment extends MeBaseFragment implements FragmentProvider {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_entry;
    }

    @Override
    public void init() {
        super.init();
        new GroupStudyEntryLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}