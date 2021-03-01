package com.gnss.teachlearnpro.profile.detail.location;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProfileLocationAdapter extends BaseQuickAdapter<MultipleItemEntity, BaseViewHolder> {


    public ProfileLocationAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.stv_fragment_location);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        SuperTextView stv = baseViewHolder.getView(R.id.stv_fragment_location);
//        View divider = baseViewHolder.getView(R.id.divider_location);
        String title = multipleItemEntity.getField(Contact.TITLE);
        String content = multipleItemEntity.getField(Contact.CONTENT_TITLE);

        stv.setLeftString(title);
        stv.setRightString(content);
    }
}
