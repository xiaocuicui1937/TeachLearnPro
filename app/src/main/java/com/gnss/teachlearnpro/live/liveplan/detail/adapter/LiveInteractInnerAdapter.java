package com.gnss.teachlearnpro.live.liveplan.detail.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.bean.InteractBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiveInteractInnerAdapter extends BaseQuickAdapter<CommentBean.DataBean, BaseViewHolder> implements LoadMoreModule {

    public LiveInteractInnerAdapter(int layoutResId, @Nullable List<CommentBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CommentBean.DataBean interactBean) {
        ImageView avatorIv = baseViewHolder.findView(R.id.iv_item_interact);
        TextView nickTv = baseViewHolder.findView(R.id.tv_item_interact_nick);
        TextView commentTv = baseViewHolder.findView(R.id.tv_item_interact_comment);

        String avatorUrl = interactBean.getImages();
        if (ObjectUtils.isNotEmpty(avatorUrl)){
            Glide.with(avatorIv.getContext()).load(avatorUrl).into(avatorIv);
        }
        nickTv.setText(interactBean.getNickname());
        commentTv.setText(interactBean.getContent());
    }
}
