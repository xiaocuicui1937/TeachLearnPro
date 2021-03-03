package com.gnss.teachlearnpro.live.liveback.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class LivePlayBackActivity extends BaseActivity implements ActivityProvider {

    private LivePlayBackLogic mLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play_back);
        mLogic = new LivePlayBackLogic(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLogic.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLogic.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLogic.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        mLogic.onBackPressed();
        super.onBackPressed();
    }
}
