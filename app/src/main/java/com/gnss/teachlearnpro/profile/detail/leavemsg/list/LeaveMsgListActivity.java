package com.gnss.teachlearnpro.profile.detail.leavemsg.list;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class LeaveMsgListActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_msg_list);
        new LeaveMsgListLogic(this);
    }
}