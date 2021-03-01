package com.gnss.teachlearnpro.course;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CourseBean;
import com.gnss.teachlearnpro.course.detail.CourseDetailActivity;

public class CourseItemClickListener implements OnItemClickListener {
    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Context context = view.getContext();

        if (context != null) {
            CourseBean.DataBean data = (CourseBean.DataBean) adapter.getData().get(position);
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra(Contact.ID, data.getId());
            ActivityUtils.startActivity(intent);

        }
    }
}
