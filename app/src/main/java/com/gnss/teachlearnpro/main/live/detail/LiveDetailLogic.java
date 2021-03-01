package com.gnss.teachlearnpro.main.live.detail;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.main.live.detail.fragment.LiveDetailFragment;

public class LiveDetailLogic extends BaseLogic {
    private ActivityProvider mProvider;

    public LiveDetailLogic(ActivityProvider provider) {
        this.mProvider = provider;
        LiveDetailFragment fragment = LiveDetailFragment.newInstance();
        loadFragment(provider,fragment,R.id.root_live_detail,fragment.getClass().getName());
    }


}
