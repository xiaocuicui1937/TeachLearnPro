package com.gnss.teachlearnpro.live.liveplan;

import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.live.liveplan.detail.PlanDetailAActivity;

public class LivePlanItemClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        MultipleItemEntity data = (MultipleItemEntity) adapter.getData().get(position);
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        instance.put(Contact.TITLE, data.getField(Contact.TITLE));
        instance.put(Contact.PlAY_URL, data.getField(Contact.PlAY_URL));
        instance.put(Contact.ID, data.getField(Contact.ID));
        ActivityUtils.startActivity(PlanDetailAActivity.class);
    }
}
