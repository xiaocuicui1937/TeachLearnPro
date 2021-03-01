package com.gnss.teachlearnpro.group.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class GroupStudyDetailActivity extends BaseActivity implements ActivityProvider {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_study_detail);
       new GroupStudyDetailLogic(this);
    }


}
