package com.gnss.teachlearnpro.profile;

import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class ProfileFragment extends MeBaseFragment implements FragmentProvider {

    private ProfileLogic mLogic;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void init() {
        super.init();
        mLogic = new ProfileLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLogic != null) {
            mLogic.forceRefresh();
        }
    }
}
