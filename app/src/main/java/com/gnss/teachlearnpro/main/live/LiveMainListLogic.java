package com.gnss.teachlearnpro.main.live;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.LiveListBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.List;

public class LiveMainListLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private LiveMainListAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private LiveMainViewModel model;
    private static final int DEFAULT_PAGE = 5;
    private int mPageIndex = 1;//默认获取第一页

    public LiveMainListLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle("直播列表");
        initRecyclerView();
        addRequestListener();
    }

    private void addRequestListener() {
        LiveMainListActivity act = (LiveMainListActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(LiveMainViewModel.class);
        showLoading("获取直播列表...");
        //获取直播列表
        model.obtainLiveList(1);


        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();

        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainLiveList(mPageIndex);
        });
        model.getLiveList().observe(act, liveListBean -> {
            handleLoadData(loadMoreModule, liveListBean);
        });
    }


    private void handleLoadData(BaseLoadMoreModule loadMoreModule, LiveListBean res) {
        hideLoading();
        List<LiveListBean.DataBean> data = res.getData();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }

        if (mPageIndex == 1) {
            mAdapter.setNewInstance(data);
        } else if (ObjectUtils.isNotEmpty(data)){
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

    private void initRecyclerView() {
        RecyclerView rv = mProvider.findViewById(R.id.rv_activity_live_main_list);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveMainListAdapter(R.layout.item_live_main, null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new LiveMainItemClickListener());
        initRefresh();
    }

    private void initRefresh() {

        refreshLayout = mProvider.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mAdapter.getRecyclerView().getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            mPageIndex = 1;
            showLoading("获取直播列表...");
            //获取直播列表
            model.obtainLiveList(mPageIndex);
        });
    }

}
