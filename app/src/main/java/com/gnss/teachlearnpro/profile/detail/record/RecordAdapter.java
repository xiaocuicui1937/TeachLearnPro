package com.gnss.teachlearnpro.profile.detail.record;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.RecordResBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<RecordResBean.DataBean, BaseViewHolder> {

    public RecordAdapter(int layoutResId, @Nullable List<RecordResBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RecordResBean.DataBean recordBean) {
        SuperTextView stv = baseViewHolder.getView(R.id.stv_fragment_location);
        stv.setPadding(20, 20, 20, 20);
        stv.setLeftString(recordBean.getTitle());
        stv.setRightString(recordBean.getCreate_time());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SizeUtils.dp2px(160), ViewGroup.LayoutParams.WRAP_CONTENT);
        stv.getLeftTextView().setLayoutParams(params);
        stv.getLeftTextView().setSingleLine(false);
    }
}
