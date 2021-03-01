package com.gnss.teachlearnpro.main.live.detail.commentlist.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.common.bean.CommentListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommentListAdapter extends BaseQuickAdapter<CommentListBean, BaseViewHolder> {

    public CommentListAdapter(int layoutResId, @Nullable List<CommentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CommentListBean commentListBean) {

    }


}
