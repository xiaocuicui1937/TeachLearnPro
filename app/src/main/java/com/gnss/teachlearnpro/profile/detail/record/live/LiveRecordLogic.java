package com.gnss.teachlearnpro.profile.detail.record.live;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.RecordResBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.profile.detail.ProfileDetailActivity;
import com.gnss.teachlearnpro.profile.detail.record.RecordAdapter;
import com.gnss.teachlearnpro.profile.detail.record.RecordViewModel;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class LiveRecordLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private RecordAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private RecordViewModel model;

    public LiveRecordLogic(FragmentProvider provider) {
        this.mProvider = provider;
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_live_plan);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new RecordAdapter(R.layout.item_location, null);
        rv.setAdapter(mAdapter);
        obtainLive();
    }

    private void obtainLive() {
        ProfileDetailActivity act = (ProfileDetailActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(RecordViewModel.class);
        model.obtainRecords(RecordViewModel.RecordType.LIVE, mPageIndex = 1);

        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainRecords(RecordViewModel.RecordType.LIVE, mPageIndex);
        });
        model.getLiveRecords().observe(act, dataBeans -> handleLoadData(loadMoreModule,dataBeans));

        initRefresh();
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, List<RecordResBean.DataBean> data) {
        hideLoading();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
        if (ObjectUtils.isEmpty(data)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(data);
        } else {
            mAdapter.addData(data);
        }

        if (data.size() < DEFAULT_PAGE) {
            //如果不够一页的话就停止加载
            loadMoreModule.loadMoreEnd();
            MeLog.e("more dengyu" + data.size());

        } else {
            MeLog.e("more dayu" + data.size());

            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }

    private void initRefresh() {
        refreshLayout = mProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showLoading("获取直播浏览记录列表...");
                model.obtainRecords(RecordViewModel.RecordType.LIVE, mPageIndex = 1);
            }
        });
    }
}
