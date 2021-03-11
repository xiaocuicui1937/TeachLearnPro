package com.gnss.teachlearnpro.group;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.UiMessageUtils;
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


        ImageView iv = baseViewHolder.getView(R.id.iv_item_group_study_lock);
        TextView tv = baseViewHolder.getView(R.id.tv_item_group_study_blur);
        boolean isBlur = multipleItemEntity.getField(Contact.BLUR);
        setBlur(titleTv, dateTv, timeTv, locationTv, groupTv, title, date, location, group, time, iv, tv, isBlur);

//        UiMessageUtils.getInstance().addListener(Contact.BLUR_VISIBLE, localMessage -> {
//            MultipleItemEntity entity = (MultipleItemEntity) localMessage.getObject();
//            setBlur(titleTv, dateTv, timeTv, locationTv, groupTv,
//                    entity.getField(Contact.TITLE), entity.getField(Contact.PlAN_DATE),
//                    entity.getField(Contact.PlAN_LOCATION), entity.getField(Contact.PlAN_GROUP),
//                    entity.getField(Contact.PlAN_TIME), iv, tv, false);
//        });
    }

    private void setBlur(TextView titleTv, TextView dateTv, TextView timeTv, TextView locationTv, TextView groupTv, String title, String date, String location, String group, String time, ImageView iv, TextView tv, boolean isBlur) {
        if (isBlur) {
            titleTv.setText("xxxxx");
            dateTv.setText("xxxxx");
            timeTv.setText("xxxxx");
            locationTv.setText("xxxxx");
            groupTv.setText("xxxxx");
        } else {
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
