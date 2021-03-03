package com.gnss.teachlearnpro.course.activity;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.course.CourseFragment;

public class CourseLogic extends BaseLogic {
    private ActivityProvider mProvider;

    public CourseLogic(ActivityProvider provider) {
        this.mProvider = provider;
        CacheMemoryUtils.getInstance().put(Contact.IS_ACTIVITY, true);
        CourseFragment courseFragment = CourseFragment.newInstance();
        loadFragment(provider, courseFragment, R.id.root_profile_detail, courseFragment.getClass().getName());
    }

}
