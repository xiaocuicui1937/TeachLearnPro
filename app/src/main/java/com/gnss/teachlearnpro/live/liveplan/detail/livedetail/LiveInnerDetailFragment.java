package com.gnss.teachlearnpro.live.liveplan.detail.livedetail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class LiveInnerDetailFragment extends MeBaseFragment implements FragmentProvider {

    private LiveInnerDetailLogic mLogic;

    public static LiveInnerDetailFragment newInstance() {

        Bundle args = new Bundle();
        LiveInnerDetailFragment fragment = new LiveInnerDetailFragment();
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
        mLogic = new LiveInnerDetailLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLogic.destroyWebView();
    }
}
