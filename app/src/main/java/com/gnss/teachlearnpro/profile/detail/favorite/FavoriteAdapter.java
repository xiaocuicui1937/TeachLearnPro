package com.gnss.teachlearnpro.profile.detail.favorite;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.FavoriteBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavoriteAdapter extends BaseQuickAdapter<FavoriteBean.DataBean, BaseViewHolder> implements LoadMoreModule {

    public FavoriteAdapter(int layoutResId, @Nullable List<FavoriteBean.DataBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.tv_item_favorite_play);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FavoriteBean.DataBean item) {
        ImageView iv = baseViewHolder.getView(R.id.iv_item_favorite);
        TextView tvTitle = baseViewHolder.getView(R.id.tv_item_favorite_title);
        TextView tvType = baseViewHolder.getView(R.id.tv_item_favorite_type);
        TextView tvCreateTime = baseViewHolder.getView(R.id.tv_item_favorite_createtime);
        Glide.with(iv.getContext()).load(item.getHead_img()).placeholder(R.drawable.ic_place_favorite).into(iv);
        tvTitle.setText(item.getTitle());
        tvType.setText(getTypeStr(item.getType()));
        tvCreateTime.setText(stampToDate(item.getCreate_time()));
    }

    public String stampToDate(int time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //如果它本来就是long类型的,则不用写这一步
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    private String getTypeStr(int type) {
        if (type == 1) {
            return StringUtils.getString(R.string.course_f);
        }
        if (type == 2) {
            return StringUtils.getString(R.string.live);
        }
        if (type == 3) {
            return StringUtils.getString(R.string.group_lean);
        }
        return "未知";
    }
}
