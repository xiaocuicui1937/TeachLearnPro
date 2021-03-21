package com.gnss.teachlearnpro.common.video;

import com.bumptech.glide.Glide;
import com.gnss.teachlearnpro.common.video.CommonVideoPlayerView;

import java.util.LinkedHashMap;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static cn.jzvd.Jzvd.backPress;


public class PlayerManager {
    private CommonVideoPlayerView mPlayer;

    public PlayerManager(CommonVideoPlayerView player) {
        this.mPlayer = player;
    }

    public void init(String url,String coverUrl, String title) {
        //使用ijk播放器内核进行播放
        Jzvd.releaseAllVideos();

        LinkedHashMap<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("高清",url);
        JZDataSource jzDataSource = new JZDataSource(map, title);
        jzDataSource.looping = true;
        jzDataSource.headerMap.put("key", "value");//header
        mPlayer.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL,JZMediaIjk.class);
        Jzvd.PROGRESS_DRAG_RATE = 2f;//设置播放进度条手势滑动阻尼系数
        Glide.with(mPlayer.getContext()).load(coverUrl).into(mPlayer.posterImageView);
        mPlayer.startPreloading(); //开始预加载，加载完等待播放
        mPlayer.startVideoAfterPreloading(); //如果预加载完会开始播放，如果未加载则开始加载

    }


    public void onVideoPause() {
        JZUtils.clearSavedProgress(mPlayer.getContext(), null);
        //home back
        Jzvd.goOnPlayOnPause();
    }

    public void onVideoResume() {
        //home back
        Jzvd.goOnPlayOnResume();
    }

    public void releaseAllVideos() {
        Jzvd.releaseAllVideos();
    }

    public void onBackPressed() {
        if (backPress()) {
            return;
        }
    }
}
