package com.gnss.teachlearnpro.profile.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class ProfileDetailActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        int type = getIntent().getIntExtra(Contact.PROFILE_DETAIL_TYPE, -1);
        new ProfileDetailLogic(this, type);

    }
}
