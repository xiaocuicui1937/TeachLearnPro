package com.gnss.teachlearnpro.course.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CourseBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CourseAdapter extends BaseQuickAdapter<CourseBean.DataBean, BaseViewHolder> {

    public CourseAdapter(int layoutResId, @Nullable List<CourseBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CourseBean.DataBean dataBean) {
        ImageView iv = baseViewHolder.getView(R.id.iv_item_course);
        TextView tvTitle = baseViewHolder.getView(R.id.tv_item_course_title);
        TextView tvDesc = baseViewHolder.getView(R.id.tv_item_course_desc);
        TextView tvTeacher = baseViewHolder.getView(R.id.tv_item_course_teacher);
        TextView tvProgress = baseViewHolder.getView(R.id.tv_item_course_progress);

        Glide.with(iv.getContext()).load( dataBean.getLogo()).into(iv);
        tvTitle.setText(dataBean.getTitle());
        tvDesc.setText(dataBean.getDesc());
        tvTeacher.setText(dataBean.getCourse_count()+"");
        tvProgress.setText(dataBean.getCatelog_title());
    }
}
