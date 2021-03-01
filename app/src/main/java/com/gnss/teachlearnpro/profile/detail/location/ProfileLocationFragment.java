package com.gnss.teachlearnpro.profile.detail.location;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class ProfileLocationFragment extends MeBaseFragment implements FragmentProvider {

    public static ProfileLocationFragment newInstance() {

        Bundle args = new Bundle();

        ProfileLocationFragment fragment = new ProfileLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_location;
    }


    @Override
    public void init() {
        super.init();
        new ProfileLocationLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
