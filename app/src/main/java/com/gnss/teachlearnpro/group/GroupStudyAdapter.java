package com.gnss.teachlearnpro.group;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GroupStudyAdapter extends BaseQuickAdapter<MultipleItemEntity, BaseViewHolder> implements LoadMoreModule {

    public GroupStudyAdapter(int layoutResId, @Nullable List<MultipleItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseTextView, MultipleItemEntity multipleItemEntity) {
        createCourseItem(baseTextView, multipleItemEntity);
    }

    private void createCourseItem(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        TextView titleTv = baseViewHolder.getView(R.id.tv_item_group_study_title);
        TextView dateTv = baseViewHolder.getView(R.id.tv_item_group_study_date);
        TextView timeTv = baseViewHolder.getView(R.id.tv_item_group_study_time);
        TextView locationTv = baseViewHolder.getView(R.id.tv_item_group_study_location);
        TextView groupTv = baseViewHolder.getView(R.id.tv_item_group_study_group);

        String title = multipleItemEntity.getField(Contact.TITLE);
        String date = multipleItemEntity.getField(Contact.PlAN_DATE);
        String location = multipleItemEntity.getField(Contact.PlAN_LOCATION);
        String group = multipleItemEntity.getField(Contact.PlAN_GROUP);
        String time = multipleItemEntity.getField(Contact.PlAN_TIME);


        boolean isBlur = multipleItemEntity.getField(Contact.BLUR);
        ImageView iv = baseViewHolder.getView(R.id.iv_item_group_study_lock);
        TextView tv = baseViewHolder.getView(R.id.tv_item_group_study_blur);
        if (isBlur) {
            titleTv.setText("xxxxx");
            dateTv.setText("xxxxx");
            timeTv.setText("xxxxx");
            locationTv.setText("xxxxx");
            groupTv.setText("xxxxx");
        }else{
            titleTv.setText(title);
            dateTv.setText(date);
            timeTv.setText(time);
            locationTv.setText(location);
            groupTv.setText(group);
        }
        iv.setVisibility(isBlur ? View.VISIBLE : View.GONE);
        tv.setVisibility(isBlur ? View.VISIBLE : View.GONE);
    }


}
