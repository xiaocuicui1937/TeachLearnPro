package com.gnss.teachlearnpro.profile.detail.record.live;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.profile.detail.ProfileDetailActivity;
import com.gnss.teachlearnpro.profile.detail.record.RecordAdapter;
import com.gnss.teachlearnpro.profile.detail.record.RecordViewModel;

public class LiveRecordLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private RecordAdapter mAdapter;

    public LiveRecordLogic(FragmentProvider provider){
        this.mProvider = provider;
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_live_plan);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new RecordAdapter(R.layout.item_location,null);
        rv.setAdapter(mAdapter);
        obtainLive();
    }

    private void obtainLive(){
        ProfileDetailActivity act = (ProfileDetailActivity) ActivityUtils.getTopActivity();
        RecordViewModel model = new ViewModelProvider(act).get(RecordViewModel.class);
        model.obtainRecords(RecordViewModel.RecordType.LIVE);
        model.getLiveRecords().observe(act, dataBeans -> mAdapter.setNewInstance(dataBeans));
    }
}
