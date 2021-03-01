package com.gnss.teachlearnpro.main.home.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.ArticleBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeInfoAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    public HomeInfoAdapter(int layoutResId, @Nullable List<ArticleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ArticleBean articleBean) {
        TextView tvIndex = baseViewHolder.getView(R.id.tv_item_airticle_item_detail_first);
        TextView tvContent = baseViewHolder.getView(R.id.tv_item_airticle_item_content);
        tvIndex.setText(articleBean.index);
        tvContent.setText(articleBean.name);
        if (getData().size() == 1) {
            tvIndex.setBackgroundResource(R.drawable.sp_circle_first);
        } else {
            tvIndex.setBackgroundResource(R.drawable.sp_circle_other);
        }
    }


}
