package com.gnss.teachlearnpro.live.liveplan.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseActivity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class PlanDetailAActivity extends BaseActivity implements ActivityProvider {


    private PlanDetailALogic mLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);
        mLogic = new PlanDetailALogic(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLogic.resumeLive();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLogic.pauseLive();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLogic.stopLive();
    }

}

