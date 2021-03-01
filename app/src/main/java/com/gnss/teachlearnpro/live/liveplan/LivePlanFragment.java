package com.gnss.teachlearnpro.live.liveplan;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class LivePlanFragment extends MeBaseFragment implements FragmentProvider {
    public static LivePlanFragment newInstance() {

        Bundle args = new Bundle();
        LivePlanFragment fragment = new LivePlanFragment();
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
        new LivePlanLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
