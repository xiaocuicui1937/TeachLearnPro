package com.gnss.teachlearnpro.gate.logic;

import androidx.fragment.app.FragmentTransaction;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.gate.fragment.LoginOrRegisterFragment;
import com.gnss.teachlearnpro.gate.fragment.SplashFragment;

public class GateLogic extends BaseLogic {
    private ActivityProvider mActivityProvider;

    public GateLogic(ActivityProvider activityProvider) {
        mActivityProvider = activityProvider;
        SplashFragment fragment = SplashFragment.newInstance();
        loadFragment(activityProvider,fragment,R.id.fl_activity_gate_content,fragment.getClass().getName());
        init();
    }

    /**
     * 初始化
     */
    private void init() {

    }
}
