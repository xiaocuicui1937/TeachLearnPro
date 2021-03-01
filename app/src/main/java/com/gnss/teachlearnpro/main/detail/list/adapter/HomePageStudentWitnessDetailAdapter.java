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

public class HomePageStudentWitnessDetailAdapter extends BaseQuickAdapter<HomeDetailBean, BaseViewHolder> implements LoadMoreModule {

    public HomePageStudentWitnessDetailAdapter(int layoutResId, @Nullable List<HomeDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeDetailBean homeDetailBean) {
        ImageView ivLogo = baseViewHolder.getView(R.id.iv_item_student_witness);
        TextView tvContent = baseViewHolder.getView(R.id.tv_item_student_witness_content);
        tvContent.setText(homeDetailBean.title);
        Glide.with(ivLogo.getContext()).load(homeDetailBean.logoUrl).placeholder(R.mipmap.ic_launcher_round)
                .into(ivLogo);

    }
}
