package com.gnss.teachlearnpro.live;

import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

/**
 * 直播
 */
public class LiveFragment extends MeBaseFragment implements FragmentProvider {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    public void init() {
        super.init();
        new LiveLogic(this);
    }


    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
