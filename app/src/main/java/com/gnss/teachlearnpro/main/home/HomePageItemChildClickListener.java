package com.gnss.teachlearnpro.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.HomePageBean;
import com.gnss.teachlearnpro.course.detail.CourseDetailActivity;
import com.gnss.teachlearnpro.main.detail.list.HomePageDetailActivity;
import com.gnss.teachlearnpro.main.live.LiveMainListActivity;
import com.gnss.teachlearnpro.main.recentstudy.RecentListActivity;

import java.util.List;
//            CacheMemoryUtils.getInstance().put(Contact.ID,55);
//            ActivityUtils.startActivity(LiveDetailActivity.class);
public class HomePageItemChildClickListener implements OnItemChildClickListener {

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
        Context context = view.getContext();
        int type = entity.getField(ItemType.TYPE);
       if (type == ItemType.LIVE_TYPE) {
            ActivityUtils.startActivity(LiveMainListActivity.class);
        } else if (type == ItemType.RECENT_GRID_TYPE) {
            toRecent(view, entity);
        } else {
            toHomePageDetail(context, entity.getField(Contact.TITLE), entity.getField(ItemType.TYPE));
        }
    }

    private void toRecent(@NonNull View view, MultipleItemEntity entity) {
        List<HomePageBean.DataBean.StudyBean> studys = entity.getField(Contact.ARRAY);
        if (view.getId() == R.id.iv_item_recent_course) {
            toRecentDetail(view, studys.get(0));
        } else if (view.getId() == R.id.iv_item_live_recent_study_live) {
            toRecentDetail(view, studys.get(1));
        } else {
            ActivityUtils.startActivity(RecentListActivity.class);
        }
    }

    private void toRecentDetail(@NonNull View view, HomePageBean.DataBean.StudyBean studyBean) {
        Intent intent = new Intent(view.getContext(), CourseDetailActivity.class);
        intent.putExtra(Contact.ID, studyBean.getId());
        ActivityUtils.startActivity(intent);
    }

    private void toHomePageDetail(Context context, String title, int type) {
        if (context == null) return;
        Intent intent = new Intent(context, HomePageDetailActivity.class);
        intent.putExtra(Contact.TITLE, title);
        intent.putExtra(Contact.HOME_DETAIL_TYPE, type);
        ActivityUtils.startActivity(intent);
    }
}
