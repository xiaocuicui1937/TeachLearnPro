package com.gnss.teachlearnpro.main.recentstudy;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LiveListBean;
import com.gnss.teachlearnpro.common.bean.RecentStudyBean;
import com.gnss.teachlearnpro.course.detail.CourseDetailActivity;
import com.gnss.teachlearnpro.main.live.detail.LiveDetailActivity;

public class RecentItemClickListener implements OnItemClickListener {
    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        RecentStudyBean.DataBean data = (RecentStudyBean.DataBean) adapter.getData().get(position);
        Intent intent = new Intent(view.getContext(),CourseDetailActivity.class);
        intent.putExtra(Contact.ID, data.getId());
        ActivityUtils.startActivity(intent);
    }
}
