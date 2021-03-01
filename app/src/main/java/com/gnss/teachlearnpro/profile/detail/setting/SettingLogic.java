package com.gnss.teachlearnpro.profile.detail.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

public class SettingLogic extends BaseLogic {
    private FragmentProvider mProvider;

    public SettingLogic(FragmentProvider provider) {
        mProvider = provider;
        setTitle(provider, "设置", (AppCompatActivity) provider.getActivity());
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_setting);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        SettingAdapter mAdapter = new SettingAdapter(R.layout.item_setting, new SettingDataConvert().convert());
        rv.setAdapter(mAdapter);
    }
}
