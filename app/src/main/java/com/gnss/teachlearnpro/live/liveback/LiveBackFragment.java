package com.gnss.teachlearnpro.live.liveback;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class LiveBackFragment extends MeBaseFragment implements FragmentProvider {
    public static LiveBackFragment newInstance() {

        Bundle args = new Bundle();
        LiveBackFragment fragment = new LiveBackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.vp_live;
    }

    @Override
    public void init() {
        super.init();
        new LiveBackLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
