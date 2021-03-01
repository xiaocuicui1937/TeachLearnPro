package com.gnss.teachlearnpro.common.ui;

import com.ecommerce.common.ui.compent.MeBaseFragment;

public abstract class LazyFragment extends MeBaseFragment {
    private boolean isFirstLoad = true; // 是否第一次加载

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            initData();
        }
    }

   public abstract void initData();
}
