package com.gnss.teachlearnpro.live.liveback.detail;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.video.PlayerManager;
import com.gnss.teachlearnpro.common.video.CommonVideoPlayerView;


public class LivePlayBackLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private PlayerManager mPlayerManager;


    public LivePlayBackLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle(CacheMemoryUtils.getInstance().get(Contact.TITLE));
        initView();
    }

    private void initView() {
        CommonVideoPlayerView player = mProvider.findViewById(R.id.sgp_top_bg);
        mPlayerManager = new PlayerManager(player);
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        mPlayerManager.init(instance.get(Contact.PlAY_URL), null, instance.get(Contact.TITLE));
    }


    public void onVideoPause() {
        mPlayerManager.onVideoPause();
    }

    public void onVideoResume() {
        mPlayerManager.onVideoResume();
    }

    public void releaseAllVideos() {
        mPlayerManager.releaseAllVideos();
    }

    public void onBackPressed() {
        mPlayerManager.onBackPressed();
    }

}
