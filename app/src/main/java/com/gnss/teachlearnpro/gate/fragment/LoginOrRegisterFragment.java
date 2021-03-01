package com.gnss.teachlearnpro.gate.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

/**
 * 登录模块
 */
public class LoginOrRegisterFragment extends MeBaseFragment implements FragmentProvider {

    public static LoginOrRegisterFragment newInstance() {

        Bundle args = new Bundle();

        LoginOrRegisterFragment fragment = new LoginOrRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {

        return R.layout.fragment_login_register;
    }

    @Override
    public void init() {
        super.init();
        new LoginOrRegisterLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
