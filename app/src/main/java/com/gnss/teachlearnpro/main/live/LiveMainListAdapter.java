package com.gnss.teachlearnpro.main.live;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.LiveListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiveMainListAdapter extends BaseQuickAdapter<LiveListBean.DataBean, BaseViewHolder> implements LoadMoreModule {

    RoundedCorners roundedCorners = new RoundedCorners(50);
    public LiveMainListAdapter(int layoutResId, @Nullable List<LiveListBean.DataBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LiveListBean.DataBean dataBean) {
        ImageView ivLogo = baseViewHolder.getView(R.id.iv_item_live_main);
        TextView tvTitle = baseViewHolder.getView(R.id.tv_item_live_main_title);
        TextView tvTime = baseViewHolder.getView(R.id.tv_item_live_list_time);
        TextView tvStatus = baseViewHolder.getView(R.id.tv_item_live_list_status);

        Glide.with(ivLogo.getContext()) .applyDefaultRequestOptions(RequestOptions.bitmapTransform(roundedCorners)).load(dataBean.getTitle_img()).into(ivLogo);
        tvTitle.setText(dataBean.getTitle());
        tvTime.setText("开始时间:" + dataBean.getTime_start() + ",结束时间:" + dataBean.getTime_end());
        tvStatus.setText(getStatus(dataBean.getStatus()));
    }

    //0未开始1直播中2结束
    private String getStatus(int type) {
        if (type == 0) {
            return "未开始";
        } else if (type == 1) {
            return "直播中";
        } else if (type == 2) {
            return "结束";
        }
        return "未知状态";
    }
}
