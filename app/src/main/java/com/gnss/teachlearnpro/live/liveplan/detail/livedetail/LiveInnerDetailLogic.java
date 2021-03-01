package com.gnss.teachlearnpro.live.liveplan.detail.livedetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LiveDetailResBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.live.liveplan.detail.adapter.LiveInnerDetailAdapter;
import com.gnss.teachlearnpro.live.liveplan.detail.model.LiveInnerDetailDataConvert;
import com.gnss.teachlearnpro.main.live.detail.fragment.LiveDetailViewModel;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

public class LiveInnerDetailLogic extends BaseLogic {
    private FragmentProvider mFragmentProvider;
    private LiveInnerDetailAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private LiveDetailViewModel model;

    public LiveInnerDetailLogic(FragmentProvider fragmentProvider) {
        mFragmentProvider = fragmentProvider;
        initRecyclerView();
        addRes();
        initRefresh();
    }

    private void addRes() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(LiveDetailViewModel.class);
        obtainLiveDetail();
        model.getLiveDetail().observe(act, new Observer<LiveDetailResBean>() {
            @Override
            public void onChanged(LiveDetailResBean liveDetailResBean) {
                hideLoading();
                refreshLayout.finishRefresh();
                if (ObjectUtils.isEmpty(liveDetailResBean)){
                    return;
                }
                mAdapter.setNewInstance(new LiveInnerDetailDataConvert(liveDetailResBean).convert());
            }
        });
    }

    private void obtainLiveDetail() {
        showLoading("获取直播详情...");
        model.obtainLiveDetail(CacheMemoryUtils.getInstance().get(Contact.ID));
    }

    private void initRecyclerView() {
        RecyclerView rv = mFragmentProvider.getMineView().findViewById(R.id.rv_fragment_live_plan);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveInnerDetailAdapter(null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new LiveInnerDetailItemClickListener());
    }

    private void initRefresh() {
        refreshLayout = mFragmentProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mFragmentProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                obtainLiveDetail();
            }
        });
    }

    public void destroyWebView() {
        mAdapter.destroyWebView();
    }
}
