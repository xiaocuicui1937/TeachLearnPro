package com.gnss.teachlearnpro.profile.detail.setting;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SettingAdapter extends BaseQuickAdapter<MultipleItemEntity, BaseViewHolder> {

    public SettingAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItemEntity s) {
        SuperTextView stv = baseViewHolder.getView(R.id.stv_fragment_setting);
        String name = s.getField(Contact.TITLE);
        boolean isOpen = s.getField(Contact.SWITCH);
        stv.setLeftString(name);
        stv.setSwitchIsChecked(isOpen);

    }
}
