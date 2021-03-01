package com.gnss.teachlearnpro.profile.detail.leavemsg;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class LeaveMsgFragment extends MeBaseFragment implements FragmentProvider {

    public static LeaveMsgFragment newInstance() {

        Bundle args = new Bundle();

        LeaveMsgFragment fragment = new LeaveMsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_leave_msg;
    }

    @Override
    public void init() {
        super.init();
        new LeaveMsgLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
