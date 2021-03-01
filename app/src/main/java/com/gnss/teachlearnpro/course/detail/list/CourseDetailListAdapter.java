package com.gnss.teachlearnpro.course.detail.list;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.PlayItemBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CourseDetailListAdapter extends BaseQuickAdapter<PlayItemBean, BaseViewHolder> {

    public CourseDetailListAdapter(int layoutResId, @Nullable List<PlayItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PlayItemBean playItemBean) {
        TextView tvCourseName = baseViewHolder.getView(R.id.tv_item_course_detail_list);
        TextView tvCourseProgress = baseViewHolder.getView(R.id.tv_item_course_detail_list_progress);
//        ImageView ivPlay = baseViewHolder.getView(R.id.iv_item_course_detail_play);

        tvCourseName.setText(playItemBean.title);
        tvCourseProgress.setText(playItemBean.progress);
    }
}
