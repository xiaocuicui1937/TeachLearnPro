package com.gnss.teachlearnpro.live.liveplan.detail.liveInteract.reward;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.gnss.teachlearnpro.R;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

public class RewardCustomView extends BottomPopupView implements View.OnClickListener {

    public RewardCustomView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_reward_custom;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_layout_reword_custom_close).setOnClickListener(this);
    }


    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * 0.6);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_layout_reword_custom_close:
                dialog.dismiss();
                break;
            case R.id.tv_layout_reward_custom_submit:
                //TODO 生成打赏二维码
                break;
            default:
                break;
        }
    }
}
