package com.gnss.teachlearnpro.live.liveback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.LivePlanBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.live.adapter.LiveBackAdapter;
import com.gnss.teachlearnpro.live.adapter.LivePlanAdapter;
import com.gnss.teachlearnpro.live.model.LivePlanDataConvert;
import com.gnss.teachlearnpro.live.model.LiveViewModel;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class LiveBackLogic extends BaseLogic {
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;
    private FragmentProvider mFragmentProvider;
    private LiveBackAdapter mAdapter;
    private LiveViewModel model;
    private RefreshLayout refreshLayout;

    public LiveBackLogic(FragmentProvider fragmentProvider) {
        mFragmentProvider = fragmentProvider;
        initRefresh();
        initRecyclerView();
        addRequestBack();
    }

    private void addRequestBack() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(LiveViewModel.class);
        model.obtainLivePlanOrBack(LiveViewModel.LiveType.BACK, 1);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        model.getLiveBack().observe(act, livePlanBean -> {
            handleLoadData(loadMoreModule, livePlanBean);
        });
        //下拉刷新
//        refreshLayout.setOnRefreshListener(refreshLayout -> {
//            //禁止上拉加载
//            loadMoreModule.setEnableLoadMore(false);
//            //重置页码
//            mPageIndex = 1;
//            showLoading("获取直播回放列表...");
//            model.obtainLivePlanOrBack(LiveViewModel.LiveType.BACK, mPageIndex);
//        });
        //上拉加载
        loadMoreModule.setOnLoadMoreListener(() -> {
            loadMoreModule.setEnableLoadMore(true);
            refreshLayout.setEnableRefresh(false);
            showLoading("获取直播回放列表...");
            model.obtainLivePlanOrBack(LiveViewModel.LiveType.BACK, mPageIndex);
        });
    }


    private void handleLoadData(BaseLoadMoreModule loadMoreModule, LivePlanBean dataBean) {
        refreshLayout.finishRefresh();
        hideLoading();
        List<LivePlanBean.DataBean> data = dataBean.getData();
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(data);
        } else {
            mAdapter.addData(data);
        }
        MeLog.e("总数：" + dataBean.getCount());
        List<LivePlanBean.DataBean> dataRes = dataBean.getData();
        if (ObjectUtils.isEmpty(dataRes)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (dataRes.size() < DEFAULT_PAGE) {
            //如果不够一页的话就停止加载
            loadMoreModule.loadMoreEnd();
            MeLog.e("more dengyu" + dataRes.size());

        } else {
            MeLog.e("more dayu" + dataRes.size());

            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }


    private void initRefresh() {
        refreshLayout = mFragmentProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mFragmentProvider.getActivity()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            mPageIndex = 1;
            showLoading("获取直播回放列表...");
            //获取直播计划列表
            model.obtainLivePlanOrBack(LiveViewModel.LiveType.BACK, mPageIndex);
        });
    }

    private void initRecyclerView() {
        RecyclerView rv = mFragmentProvider.getMineView().findViewById(R.id.rv_fragment_live_plan);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveBackAdapter(R.layout.item_plan_back,null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new LiveBackItemClickListener());
        ConstraintLayout rootLayout = mFragmentProvider.getMineView().findViewById(R.id.root_live_layout);
        fixContent(rootLayout, mFragmentProvider.getResources());
    }


}
