package com.gnss.teachlearnpro.main.home.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.HomePageBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeClassesAdapter extends BaseQuickAdapter<HomePageBean.DataBean.ClassBean, BaseViewHolder> {


    public HomeClassesAdapter(int layoutResId, @Nullable List<HomePageBean.DataBean.ClassBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomePageBean.DataBean.ClassBean classBean) {
        ImageView iv = baseViewHolder.getView(R.id.iv_item_grid);
        TextView tv = baseViewHolder.getView(R.id.tv_item_grid);
        Glide.with(iv).load(classBean.getIcon()).placeholder(R.drawable.ic_classes).into(iv);
        tv.setText(classBean.getName());
    }
}
