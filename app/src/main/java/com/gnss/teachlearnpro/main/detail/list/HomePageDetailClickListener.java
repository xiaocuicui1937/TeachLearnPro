package com.gnss.teachlearnpro.main.detail.list;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.HomeDetailBean;
import com.gnss.teachlearnpro.main.detail.detail.HomeListDetailActivity;
import com.gnss.teachlearnpro.main.detail.detail.HomeListViewModel;

public class HomePageDetailClickListener implements OnItemClickListener {
    HomeListViewModel.HomeListType mType;

    public HomePageDetailClickListener(HomeListViewModel.HomeListType type) {
        mType = type;
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Context context = view.getContext();
        if (context != null) {
            HomeDetailBean data = (HomeDetailBean) adapter.getData().get(position);
            Intent intent = new Intent(context, HomeListDetailActivity.class);
            intent.putExtra(Contact.ID, data.id);
            intent.putExtra(Contact.HOME_DETAIL_TYPE, mType);
            ActivityUtils.startActivity(intent);
        }
    }
}
