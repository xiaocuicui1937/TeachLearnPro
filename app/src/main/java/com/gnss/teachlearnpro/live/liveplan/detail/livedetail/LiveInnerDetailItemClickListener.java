package com.gnss.teachlearnpro.live.liveplan.detail.livedetail;

import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.main.live.detail.LiveDetailActivity;

public class LiveInnerDetailItemClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ActivityUtils.startActivity(LiveDetailActivity.class);
    }
}
