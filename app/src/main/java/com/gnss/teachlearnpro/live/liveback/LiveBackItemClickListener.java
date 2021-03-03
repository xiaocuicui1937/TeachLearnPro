package com.gnss.teachlearnpro.live.liveback;

import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LivePlanBean;
import com.gnss.teachlearnpro.live.liveback.detail.LivePlayBackActivity;
import com.gnss.teachlearnpro.live.liveplan.detail.PlanDetailAActivity;

import java.util.List;

public class LiveBackItemClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        LivePlanBean.DataBean data = (LivePlanBean.DataBean) adapter.getData().get(position);
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        instance.put(Contact.TITLE, data.getTitle());
        instance.put(Contact.PlAY_URL, data.getPlayback_address());
        ActivityUtils.startActivity(LivePlayBackActivity.class);
    }
}
