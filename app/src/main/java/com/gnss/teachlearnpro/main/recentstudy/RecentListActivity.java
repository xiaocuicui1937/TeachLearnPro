package com.gnss.teachlearnpro.main.recentstudy;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class RecentListActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_main_list);
        new RecentListLogic(this);
    }
}
