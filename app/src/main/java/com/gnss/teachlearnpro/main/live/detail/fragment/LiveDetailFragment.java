package com.gnss.teachlearnpro.main.live.detail.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class LiveDetailFragment extends MeBaseFragment implements FragmentProvider {

    private LiveDetailFragmentLogic mLogic;

    public static LiveDetailFragment newInstance() {

        Bundle args = new Bundle();

        LiveDetailFragment fragment = new LiveDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {

        return R.layout.fragment_live_detail;
    }

    @Override
    public void init() {
        mLogic = new LiveDetailFragmentLogic(this, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLogic.resumeLive();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLogic.pauseLive();
    }


    @Override
    public void onDestroyView() {
        mLogic.stopLive();
        super.onDestroyView();

    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
