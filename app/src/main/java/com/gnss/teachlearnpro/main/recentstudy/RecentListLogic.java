package com.gnss.teachlearnpro.main.recentstudy;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.HomePageBean;
import com.gnss.teachlearnpro.common.bean.LiveListBean;
import com.gnss.teachlearnpro.common.bean.RecentStudyBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.main.home.HomePageViewModel;
import com.gnss.teachlearnpro.main.live.LiveMainViewModel;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.List;

public class RecentListLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private RecentAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private HomePageViewModel model;
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页

    public RecentListLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle("最近课程列表");
        initRecyclerView();
        addRequestListener();
    }

    private void addRequestListener() {
        RecentListActivity act = (RecentListActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(HomePageViewModel.class);
        showLoading("获取最近课程列表...");
        //获取最近课程列表
        model.obtainRecentStudyList(1);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainRecentStudyList(mPageIndex);
        });
        model.getRecentStudyList().observe(act, courseBean -> handleLoadData(loadMoreModule, courseBean));
    }


    private void handleLoadData(BaseLoadMoreModule loadMoreModule, RecentStudyBean res) {
        hideLoading();
        List<RecentStudyBean.DataBean> data = res.getData();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
        if (ObjectUtils.isEmpty(data)){
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(data);
        } else if (ObjectUtils.isNotEmpty(data)) {
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
        mAdapter = new RecentAdapter(R.layout.item_hot_course, null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecentItemClickListener());
        initRefresh();
    }

    private void initRefresh() {

        refreshLayout = mProvider.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mAdapter.getRecyclerView().getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            mPageIndex = 1;
            showLoading("获取最近课程列表...");
            //获取直播列表
            model.obtainRecentStudyList(mPageIndex);
        });
    }

}
