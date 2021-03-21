package com.gnss.teachlearnpro.common.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.RadioGroupEx;

import cn.jzvd.JzvdStd;

public class CommonVideoPlayerView extends JzvdStd {
    ImageView mIvBack10, mIvForward10, mIvBack, mIvForward, mIvScreenSwitch, mIvShare;
    TextView mTvMultiple;
    private RelativeLayout mPlayLayout;
    private RadioGroupEx radioGroupEx;

    public CommonVideoPlayerView(Context context) {
        super(context);

    }

    public CommonVideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_common_video;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        mIvBack10 = findViewById(R.id.iv_common_video_back_10);
        mIvForward10 = findViewById(R.id.iv_common_video_front_10);
        mIvBack = findViewById(R.id.iv_common_video_back);
        mIvForward = findViewById(R.id.iv_common_video_front);
        mIvScreenSwitch = findViewById(R.id.fullscreen_small);
        mIvShare = findViewById(R.id.share);
        mPlayLayout = findViewById(R.id.layout_bottom_play);
        mTvMultiple = findViewById(R.id.multiple);
        radioGroupEx = findViewById(R.id.rgx_layout_common_video);

        titleTextView.setVisibility(INVISIBLE);
        fullscreenButton.setVisibility(GONE);
        handleVideoSpeed();
        mIvScreenSwitch.setOnClickListener(view -> clickFullscreen());

        mIvForward10.setOnClickListener(view -> adjustVideoProgress(true));
        mIvBack10.setOnClickListener(view -> adjustVideoProgress(false));
        replayTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                startPreloading();
//                startVideoAfterPreloading();
                startVideo();
            }
        });
    }

    /**
     * 快进或者后退进度 调整视频播放的进度还有seek的显示位置
     *
     * @param isAdd true 快进 false 后退
     */
    private void adjustVideoProgress(boolean isAdd) {
        long duration = getDuration();
        long addPlayPosition = isAdd ? getCurrentPositionWhenPlaying() + 10 * 1000
                : getCurrentPositionWhenPlaying() - 10 * 1000;
        mediaInterface.seekTo(addPlayPosition);
        int progress = (int) (addPlayPosition * 100 / duration);
        progressBar.setProgress(progress);
    }

    /**
     * 设置视频播放速度的功能
     */
    private void handleVideoSpeed() {
        mTvMultiple.setOnClickListener(view -> radioGroupEx.setVisibility(VISIBLE));
        SPUtils instance = SPUtils.getInstance();
        int selectedSpeedIndex = instance.getInt(Contact.VIDEO_SPEED_INDEX);
        String speedFromIndex = String.valueOf(getSpeedFromIndex(selectedSpeedIndex))
                .replace("f", "") + "x";
        mTvMultiple.setText(speedFromIndex);
        radioGroupEx.check(selectedSpeedIndex);
        radioGroupEx.setOnCheckedChangeListener((radioGroup, checkId) -> {
            mediaInterface.setSpeed(getSpeedFromIndex(checkId));
            instance.put(Contact.VIDEO_SPEED_INDEX, checkId);
            String speedStr = String.valueOf(getSpeedFromIndex(checkId))
                    .replace("f", "") + "x";
            mTvMultiple.setText(speedStr);
            radioGroupEx.setVisibility(GONE);
        });
    }

    /**
     * 根据选中的速度item获取对应速度的大小
     *
     * @param checkId RadioButton id
     * @return 速度的大小
     */
    private float getSpeedFromIndex(int checkId) {
        float ret = 0f;
        if (checkId == R.id.rb_video_1) {
            ret = 1.0f;
        } else if (checkId == R.id.rb_video_2) {
            ret = 1.25f;
        } else if (checkId == R.id.rb_video_3) {
            ret = 1.5f;
        } else if (checkId == R.id.rb_video_4) {
            ret = 1.75f;
        } else if (checkId == R.id.rb_video_5) {
            ret = 2.0f;
        }
        return ret;
    }

    @Override
    public void setScreenNormal() {
        super.setScreenNormal();
        setMargin(53, 10, 10, 0);
        titleTextView.setVisibility(INVISIBLE);
        fullscreenButton.setVisibility(GONE);
    }

    private void setMargin(int widthPx, int leftMarginPx, int topMarginPx, int rightMarginPx) {
        int width = SizeUtils.dp2px(widthPx);
        int leftMargin = SizeUtils.dp2px(leftMarginPx);
        int topMargin = SizeUtils.dp2px(topMarginPx);
        int rightMargin = SizeUtils.dp2px(rightMarginPx);
        RelativeLayout.LayoutParams paramsScreen = new RelativeLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsScreen.leftMargin = leftMargin;
        paramsScreen.topMargin = topMargin;
        RelativeLayout.LayoutParams paramsShare = new RelativeLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsShare.rightMargin = rightMargin;
        paramsShare.topMargin = topMargin;
        paramsShare.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mIvScreenSwitch.setLayoutParams(paramsScreen);
        mIvShare.setLayoutParams(paramsShare);
    }

    @Override
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        int titleWidth = titleTextView.getWidth() / 2;
        if (titleWidth < 100) {
            titleWidth = 100;
        }
        setMargin(53, titleWidth, 7, 40);
        titleTextView.setVisibility(VISIBLE);
        fullscreenButton.setVisibility(GONE);
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int posterImg, int bottomPro, int retryLayout) {
        super.setAllControlsVisiblity(topCon, bottomCon, startBtn, loadingPro, posterImg, bottomPro, retryLayout);
        mPlayLayout.setVisibility(bottomCon);
        mIvShare.setVisibility(bottomCon);
        mIvScreenSwitch.setVisibility(bottomCon);
    }


    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                mPlayLayout.setVisibility(INVISIBLE);
                mIvShare.setVisibility(INVISIBLE);
                mIvScreenSwitch.setVisibility(INVISIBLE);
                if (screen != SCREEN_TINY) {
                    bottomProgressBar.setVisibility(View.VISIBLE);
                }
                cancelProgressTimer();
            });
        }

    }

    @Override
    protected void clickSurfaceContainer() {
        super.clickSurfaceContainer();
        radioGroupEx.setVisibility(GONE);
    }
}
