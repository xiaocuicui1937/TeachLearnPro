package com.gnss.teachlearnpro.gate.logic;

import com.blankj.utilcode.util.ThreadUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.gate.fragment.LoginOrRegisterFragment;

import java.util.concurrent.TimeUnit;

public class SplashLogic extends BaseLogic {
    private FragmentProvider mProvider;

    public SplashLogic(FragmentProvider provider) {
        this.mProvider = provider;
        delayToLogin();
    }

    /**
     * 延迟两秒进入首页
     */
    private void delayToLogin() {
        ThreadUtils.executeBySingleWithDelay(new ThreadUtils.SimpleTask<String>() {
            @Override
            public String doInBackground() throws Throwable {
                return null;
            }

            @Override
            public void onSuccess(String result) {
                replaceFragment(LoginOrRegisterFragment.newInstance(), R.id.fl_activity_gate_content);
            }
        }, 2, TimeUnit.SECONDS);
    }
}
