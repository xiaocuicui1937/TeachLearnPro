package com.gnss.teachlearnpro.course.detail.list;

import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.PlayItemBean;
import com.gnss.teachlearnpro.course.detail.play.CourseDetailPlayActivity;

public class CourseDetailItemClickListener implements OnItemClickListener {
    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        PlayItemBean playItemBean = (PlayItemBean) adapter.getData().get(position);
        CacheMemoryUtils.getInstance().put(Contact.PlAY_URL, playItemBean.url);
        CacheMemoryUtils.getInstance().put(Contact.TITLE, playItemBean.title);
        CacheMemoryUtils.getInstance().put(Contact.ID, playItemBean.id);
        CacheMemoryUtils.getInstance().put(Contact.IS_COLLECT, playItemBean.isCollect);

        ActivityUtils.startActivity(CourseDetailPlayActivity.class);
    }
}
