package com.gnss.teachlearnpro.main.recentstudy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.RecentStudyBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecentAdapter extends BaseQuickAdapter<RecentStudyBean.DataBean, BaseViewHolder> implements LoadMoreModule {

    public RecentAdapter(int layoutResId, @Nullable List<RecentStudyBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecentStudyBean.DataBean dataBean) {
        ImageView ivLogo = baseViewHolder.getView(R.id.iv_item_hot_course);
        TextView tvContentTitle = baseViewHolder.getView(R.id.tv_item_hot_course_content);
        TextView tvContentOther = baseViewHolder.getView(R.id.tv_item_hot_course_other);
        TextView title = baseViewHolder.getView(R.id.tv_item_hot_course);
        title.setVisibility(View.INVISIBLE);


        String logoUrl = dataBean.getLogo();
        String contentTitle = dataBean.getTitle();
//        String contentSubTitle = dataBean.get();
        Glide.with(ivLogo.getContext()).load(logoUrl).into(ivLogo);
        tvContentTitle.setText(contentTitle);
        tvContentOther.setText("");
    }
}
