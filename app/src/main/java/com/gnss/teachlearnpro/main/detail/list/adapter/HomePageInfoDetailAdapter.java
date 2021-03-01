package com.gnss.teachlearnpro.main.detail.list.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.HomeDetailBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomePageInfoDetailAdapter extends BaseQuickAdapter<HomeDetailBean, BaseViewHolder> implements LoadMoreModule {

    public HomePageInfoDetailAdapter(int layoutResId, @Nullable List<HomeDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeDetailBean homeDetailBean) {
        ImageView ivLogo = baseViewHolder.getView(R.id.iv_item_airticle_item_detail_logo);

        TextView tvContent = baseViewHolder.getView(R.id.tv_item_airticle_item_detail_content);
        TextView tvAuthor = baseViewHolder.getView(R.id.tv_item_airticle_item_detail_author);
        TextView tvCounts = baseViewHolder.getView(R.id.tv_item_airticle_item_detail_read_collect);
        tvAuthor.setText(homeDetailBean.index);
        tvContent.setText(homeDetailBean.title);
        tvCounts.setText("阅读数:" + homeDetailBean.readCount + "   收藏数:" + homeDetailBean.collectCount);

        Glide.with(ivLogo.getContext()).load(homeDetailBean.logoUrl).into(ivLogo);
    }
}
