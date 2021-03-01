package com.gnss.teachlearnpro.profile.detail.record;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class RecordFragment extends MeBaseFragment implements FragmentProvider {

    public static RecordFragment newInstance() {

        Bundle args = new Bundle();

        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    public void init() {
        super.init();
        new RecordLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
