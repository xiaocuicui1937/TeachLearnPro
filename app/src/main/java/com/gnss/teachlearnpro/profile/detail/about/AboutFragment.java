package com.gnss.teachlearnpro.profile.detail.about;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class AboutFragment extends MeBaseFragment implements FragmentProvider {

    private AboutLogic mAboutLogic;

    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public void init() {
        mAboutLogic = new AboutLogic(this);
    }

    @Override
    public void onDestroyView() {
        mAboutLogic.destroyWebView();
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
