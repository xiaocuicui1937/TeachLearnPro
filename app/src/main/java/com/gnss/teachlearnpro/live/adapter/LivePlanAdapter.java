package com.gnss.teachlearnpro.live.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LivePlanAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, BaseViewHolder> implements LoadMoreModule {

    public LivePlanAdapter(@Nullable List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.IMAGE_TYPE, R.layout.item_image);
        addItemType(ItemType.LIVE_PLAN_TYPE, R.layout.item_live_plan);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        switch (itemViewType) {
            case ItemType.IMAGE_TYPE:
                createLiveImage(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.LIVE_PLAN_TYPE:
                createLivePlanItem(baseViewHolder, multipleItemEntity);
                break;
            default:
                break;
        }
    }

    private void createLivePlanItem(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        TextView titleTv = baseViewHolder.getView(R.id.tv_item_live_plan_title);
        TextView dateTv = baseViewHolder.getView(R.id.tv_item_live_plan_date);
        TextView timeTv = baseViewHolder.getView(R.id.tv_item_live_plan_time);
        TextView locationTv = baseViewHolder.getView(R.id.tv_item_live_plan_location);
        TextView groupTv = baseViewHolder.getView(R.id.tv_item_live_plan_group);
        String title = multipleItemEntity.getField(Contact.TITLE);
        String date = multipleItemEntity.getField(Contact.PlAN_DATE);
        String location = multipleItemEntity.getField(Contact.PlAN_LOCATION);
        String group = multipleItemEntity.getField(Contact.PlAN_GROUP);
        String time = multipleItemEntity.getField(Contact.PlAN_TIME);
        titleTv.setText(title);
        dateTv.setText(date);
        timeTv.setText(time);
        locationTv.setText(location);
        groupTv.setText(group);
    }

    private void createLiveImage(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        ImageView iv = (ImageView) baseViewHolder.itemView;
        String img = multipleItemEntity.getField(Contact.PlAN_URL);
        if (ObjectUtils.isEmpty(img)) {
            iv.setImageResource(R.drawable.test_live_plan);
        } else {
            Glide.with(iv.getContext()).load(img).into(iv);
        }
    }
}
