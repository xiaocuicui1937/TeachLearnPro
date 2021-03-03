package com.gnss.teachlearnpro.live.liveback.detail;

import android.content.pm.ActivityInfo;
import android.view.View;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class PlayerManager {
    private StandardGSYVideoPlayer mPlayer;
    private OrientationUtils orientationUtils;

    public PlayerManager(StandardGSYVideoPlayer player) {
        mPlayer = player;
    }

    public void init(String url, String title) {
        mPlayer.setUp(url, true, title);
        //增加title
        mPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        mPlayer.getBackButton().setVisibility(View.GONE);
        //设置旋转
//        orientationUtils = new OrientationUtils(ActivityUtils.getTopActivity(), mPlayer);
//        orientationUtils.setScreenType(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        mPlayer.setAutoFullWithSize(true);
        mPlayer.getFullscreenButton().setOnClickListener(v -> {
//            orientationUtils.resolveByClick();
            mPlayer.startWindowFullscreen(mPlayer.getContext(),true,true);
        });

        //是否可以滑动调整
        mPlayer.setIsTouchWiget(true);
        mPlayer.startPlayLogic();
    }

    public StandardGSYVideoPlayer getPlayer() {
        if (mPlayer == null) {
            throw new RuntimeException("please create player first!");
        }
        return mPlayer;
    }

    public void onVideoPause() {
        mPlayer.onVideoPause();
    }

    public void onVideoResume() {
        mPlayer.onVideoResume();
    }

    public void releaseAllVideos() {
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }

    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        mPlayer.setVideoAllCallBack(null);
    }
}
