package com.gnss.teachlearnpro.group.entry;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.GroupStudyBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GroupStudyEntryAdapter extends BaseQuickAdapter<GroupStudyBean.DataBean, BaseViewHolder> {

    StringBuilder mSb;

    public GroupStudyEntryAdapter(int layoutResId, @Nullable List<GroupStudyBean.DataBean> data) {
        super(layoutResId, data);
        mSb = new StringBuilder();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GroupStudyBean.DataBean item) {
        CheckBox cb = baseViewHolder.getView(R.id.cb_item_group_entry);
        TextView tv = baseViewHolder.getView(R.id.tv_item_group_entry);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mSb.append(item.getId()).append(",");
                } else {
                    int length = String.valueOf(item.getId()).length();
                    int start = mSb.indexOf(String.valueOf(item.getId()));
                    mSb.delete(start,start+length+1);
                }
                CacheMemoryUtils.getInstance().put(Contact.GROUP_SELECTED_PARAM, mSb.toString());
            }
        });
        tv.setText(item.getTitle());
    }
}

