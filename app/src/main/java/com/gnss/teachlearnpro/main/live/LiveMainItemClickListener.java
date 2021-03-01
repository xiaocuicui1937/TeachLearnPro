package com.gnss.teachlearnpro.main.live;

import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LiveListBean;
import com.gnss.teachlearnpro.main.live.detail.LiveDetailActivity;

public class LiveMainItemClickListener implements OnItemClickListener {
    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        LiveListBean.DataBean data = (LiveListBean.DataBean) adapter.getData().get(position);
        CacheMemoryUtils.getInstance().put(Contact.ID,data.getId());
        ActivityUtils.startActivity(LiveDetailActivity.class);
    }
}
