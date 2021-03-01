package com.gnss.teachlearnpro.live.liveplan.detail.liveInteract;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class LiveInteractInnerFragment extends MeBaseFragment implements FragmentProvider {
    public static LiveInteractInnerFragment newInstance() {

        Bundle args = new Bundle();
        LiveInteractInnerFragment fragment = new LiveInteractInnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.vp_live_interact;
    }

    @Override
    public void init() {
        super.init();
        new LiveInteractInnerLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
