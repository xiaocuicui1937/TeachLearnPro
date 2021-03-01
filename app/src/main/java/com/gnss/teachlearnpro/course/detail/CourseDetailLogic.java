package com.gnss.teachlearnpro.course.detail;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.course.detail.list.CourseDetailListFragment;

public class CourseDetailLogic extends BaseLogic {
    private ActivityProvider mProvider;

    public CourseDetailLogic(ActivityProvider provider) {
        mProvider = provider;
        CourseDetailListFragment fragment = CourseDetailListFragment.newInstance(provider.getIntent().getIntExtra(Contact.ID,-1));
        loadFragment(provider, fragment, R.id.root_course_detail, fragment.getClass().getName());
    }

}
