package com.gnss.teachlearnpro.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.course.detail.CourseDetailActivity;
import com.gnss.teachlearnpro.main.detail.detail.HomeListDetailActivity;
import com.gnss.teachlearnpro.main.detail.detail.HomeListViewModel;
import com.gnss.teachlearnpro.main.live.detail.LiveDetailActivity;

public class HomePageItemClickListener implements OnItemClickListener {
    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Context context = view.getContext();
        MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
        int type = entity.getField(ItemType.TYPE);
        switch (type) {
            case ItemType.LIVE_TYPE:
                toLiveDetail(entity);
                break;
            case ItemType.HOT_COURSE_TYPE:
            case ItemType.NEW_COURSE_TYPE:
                toCourse(context, entity);
                break;
//            case ItemType.ARTICLE_TYPE:
//                toHomeDetail(context, entity, HomeListViewModel.HomeListType.INFO);
//                break;
            case ItemType.STUDENT_WITNESS_TYPE:
                toHomeDetail(context, entity, HomeListViewModel.HomeListType.STUDENT_WITNESS);
                break;
            default:
                break;
        }
    }

    private void toHomeDetail(Context context, MultipleItemEntity entity, HomeListViewModel.HomeListType type) {
        Intent intent = new Intent(context, HomeListDetailActivity.class);
        int id = entity.getField(Contact.ID);
        intent.putExtra(Contact.ID, id);
        intent.putExtra(Contact.TITLE, (String) entity.getField(Contact.CONTENT_TITLE));
        intent.putExtra(Contact.HOME_DETAIL_TYPE, type);
        ActivityUtils.startActivity(intent);
    }

    private void toCourse(Context context, MultipleItemEntity entity) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        int id = entity.getField(Contact.ID);
        intent.putExtra(Contact.ID, id);
        ActivityUtils.startActivity(intent);
    }

    private void toLiveDetail(MultipleItemEntity entity) {
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        instance.put(Contact.ID, entity.getField(Contact.ID));
        instance.put(Contact.TITLE, entity.getField(Contact.TITLE));
        ActivityUtils.startActivity(LiveDetailActivity.class);
    }
}
