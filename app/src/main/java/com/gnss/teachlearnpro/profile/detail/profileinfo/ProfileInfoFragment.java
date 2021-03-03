package com.gnss.teachlearnpro.profile.detail.profileinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UiMessageUtils;
import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.yalantis.ucrop.UCrop;

import static android.app.Activity.RESULT_OK;

public class ProfileInfoFragment extends MeBaseFragment implements FragmentProvider {


    public static ProfileInfoFragment newInstance() {

        Bundle args = new Bundle();

        ProfileInfoFragment fragment = new ProfileInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile_info;
    }

    @Override
    public void init() {
        super.init();
        new ProfileInfoLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }

}
