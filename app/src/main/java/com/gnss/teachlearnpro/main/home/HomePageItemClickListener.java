package com.gnss.teachlearnpro.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.main.detail.list.HomePageDetailActivity;
import com.gnss.teachlearnpro.main.live.LiveMainListActivity;
import com.gnss.teachlearnpro.main.live.detail.LiveDetailActivity;

import java.util.List;

public class HomePageItemClickListener implements OnItemClickListener {
    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Context context = view.getContext();
        List<MultipleItemEntity> data = (List<MultipleItemEntity>) adapter.getData();
        if (ObjectUtils.isEmpty(data)) {
            return;
        }

        MultipleItemEntity entity = data.get(position);
        if ((int) entity.getField(ItemType.TYPE) == ItemType.LIVE_TYPE) {
            ActivityUtils.startActivity(LiveMainListActivity.class);
//            CacheMemoryUtils.getInstance().put(Contact.ID,55);
//            ActivityUtils.startActivity(LiveDetailActivity.class);
        }else{
            toHomePageDetail(context, entity.getField(Contact.TITLE), entity.getField(ItemType.TYPE));
        }
    }

    private void toHomePageDetail(Context context, String title, int type) {
        if (context == null) return;
        Intent intent = new Intent(context, HomePageDetailActivity.class);
        intent.putExtra(Contact.TITLE, title);
        intent.putExtra(Contact.HOME_DETAIL_TYPE, type);
        ActivityUtils.startActivity(intent);

    }

}
