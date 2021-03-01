
package com.gnss.teachlearnpro.profile.detail.record.course;

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

public class CourseRecordLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private RecordAdapter mAdapter;

    public CourseRecordLogic(FragmentProvider provider) {
        this.mProvider = provider;
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_live_plan);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new RecordAdapter(R.layout.item_location, null);
        rv.setAdapter(mAdapter);
        obtainCourse();
    }

    private void obtainCourse(){
        ProfileDetailActivity act = (ProfileDetailActivity) ActivityUtils.getTopActivity();
        RecordViewModel model = new ViewModelProvider(act).get(RecordViewModel.class);
        model.obtainRecords(RecordViewModel.RecordType.COURSE);
        model.getCourseRecords().observe(act, dataBeans -> mAdapter.setNewInstance(dataBeans));
    }
}
