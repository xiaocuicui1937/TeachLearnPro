package com.gnss.teachlearnpro.profile.detail.leavemsg.list;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.LeaveMsgListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LeaveMsgListAdapter extends BaseQuickAdapter<LeaveMsgListBean, BaseViewHolder> implements LoadMoreModule {

    public LeaveMsgListAdapter(int layoutResId, @Nullable List<LeaveMsgListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LeaveMsgListBean leaveMsgListBean) {
        TextView tvTitle = baseViewHolder.getView(R.id.tv_item_leave_msg_title);
        TextView tvDate = baseViewHolder.getView(R.id.tv_item_leave_msg_date);
        TextView tvContent = baseViewHolder.getView(R.id.tv_item_leave_msg_content);
    }
}
