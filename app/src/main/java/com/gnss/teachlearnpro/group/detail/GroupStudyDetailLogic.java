package com.gnss.teachlearnpro.group.detail;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.group.detail.fragment.GroupStudyDetailFragment;

public class GroupStudyDetailLogic extends BaseLogic {


    public GroupStudyDetailLogic(ActivityProvider provider) {
        loadFragment(provider,GroupStudyDetailFragment.newInstance(),
                R.id.root_group_study_detail, GroupStudyDetailFragment.class.getName());
    }


}
