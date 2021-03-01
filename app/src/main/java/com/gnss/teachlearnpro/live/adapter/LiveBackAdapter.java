package com.gnss.teachlearnpro.live.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.LivePlanBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiveBackAdapter extends BaseQuickAdapter<LivePlanBean.DataBean, BaseViewHolder> implements LoadMoreModule {

    public LiveBackAdapter(int layoutResId, @Nullable List<LivePlanBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LivePlanBean.DataBean item) {
        TextView mTvTitle = baseViewHolder.getView(R.id.tv_item_plan_back_title);
        TextView mTvTeacher = baseViewHolder.getView(R.id.tv_item_plan_back_teach_teacher);
        TextView mTvTime = baseViewHolder.getView(R.id.tv_item_plan_back_teach_time);

        ImageView ivAvator = baseViewHolder.getView(R.id.iv_item_plan_back_avator);
//        ImageView ivRefresh = baseViewHolder.getView(R.id.iv_item_plan_back_refresh);
        Glide.with(ivAvator.getContext()).load(item.getTitle_img()).into(ivAvator);
        mTvTitle.setText(item.getTitle());
        mTvTeacher.setText(item.getTime_start());
        mTvTime.setText(item.getTime_start());

    }
}
