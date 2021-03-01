package com.gnss.teachlearnpro.profile.detail.leavemsg.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class LeaveMsgDetailActivity extends BaseActivity implements ActivityProvider {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_msg_detail);
        new LeaveMsgDetailLogic(this);
    }
}
