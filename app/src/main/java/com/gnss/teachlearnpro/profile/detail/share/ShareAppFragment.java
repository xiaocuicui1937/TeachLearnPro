package com.gnss.teachlearnpro.profile.detail.share;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class ShareAppFragment extends MeBaseFragment implements FragmentProvider {

    public static ShareAppFragment newInstance() {

        Bundle args = new Bundle();

        ShareAppFragment fragment = new ShareAppFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_share_app;
    }

    @Override
    public void init() {
        super.init();
        new ShareAppLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
