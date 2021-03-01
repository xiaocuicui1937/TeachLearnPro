package com.gnss.teachlearnpro.profile.detail.location.next;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.profile.detail.location.ProfileLocationLogic;

public class ProfileNextLocationFragment extends MeBaseFragment implements FragmentProvider {

    public static ProfileNextLocationFragment newInstance() {

        Bundle args = new Bundle();

        ProfileNextLocationFragment fragment = new ProfileNextLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_next_location;
    }


    @Override
    public void init() {
        super.init();
        new ProfileNextLocationLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
