package com.gnss.teachlearnpro.main.live.detail.fragment;

import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class LivePlayerManager {
    private TXCloudVideoView mPlayerView;
    private TXLivePlayer mLivePlayer;

    public LivePlayerManager(TXCloudVideoView playerView) {
        mPlayerView = playerView;
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(playerView.getContext());
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(playerView);
    }

    public void setPlayerOrientation(int rotation){
        mLivePlayer.setRenderRotation(rotation);
    }

    public void playLive(String liveUrl) {
        mLivePlayer.startPlay(liveUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV);
    }

    public void resumeLive() {
        // 继续
        mLivePlayer.resume();
    }

    public void pauseLive() {
        // 暂停
        mLivePlayer.pause();
    }

    /**
     * 结束直播
     */
    public void stopLive(){
        mLivePlayer.stopPlay(true); // true 代表清除最后一帧画面
        mPlayerView.onDestroy();
    }
}
