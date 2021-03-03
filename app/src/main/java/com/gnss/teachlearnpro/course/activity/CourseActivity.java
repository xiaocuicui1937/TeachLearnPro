package com.gnss.teachlearnpro.course.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class CourseActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        new CourseLogic(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheMemoryUtils.getInstance().put(Contact.IS_ACTIVITY, false);
        CacheMemoryUtils.getInstance().put(Contact.ID, 0);
        CacheMemoryUtils.getInstance().remove(Contact.TITLE);
    }
}
