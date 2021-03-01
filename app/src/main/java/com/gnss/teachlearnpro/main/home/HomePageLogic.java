package com.gnss.teachlearnpro.main.home;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.main.MainActivity;
import com.gnss.teachlearnpro.main.home.adapter.HomePageAdapter;
import com.gnss.teachlearnpro.main.home.data.HomePageDataConvert;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class HomePageLogic extends BaseLogic {
    private FragmentProvider fragmentProvider;
    private HomePageViewModel mModel;
    private RecyclerView mRv;
    private HomePageAdapter mAdapter;
    private RefreshLayout refreshLayout;

    public HomePageLogic(FragmentProvider fragmentProvider) {
        this.fragmentProvider = fragmentProvider;
        MainActivity act = (MainActivity) fragmentProvider.getMineView().getContext();
        mModel = new ViewModelProvider(act).get(HomePageViewModel.class);
        initView();
        initRes();
    }

    private void initRes() {
        mModel.getHomeLiveData().observe(fragmentProvider.getViewLifecycleOwner(), homePageBean -> {
            hideLoading();
            refreshLayout.finishRefresh(true);
            if (ObjectUtils.isEmpty(homePageBean)) {
                return;
            }

            if (homePageBean.isSuccess()) {
                HomePageDataConvert homePageDataConvert = new HomePageDataConvert();
                mAdapter.setNewInstance(homePageDataConvert.setJsonData(GsonUtils.toJson(homePageBean)).convert());
            } else {
                ToastUtils.showShort(homePageBean.getMsg());
            }
        });
    }

    private void initView() {
        mRv = fragmentProvider.getMineView().findViewById(R.id.rv_fragment_home);
        LinearLayoutManager manager = new LinearLayoutManager(mRv.getContext());
        mRv.setLayoutManager(manager);
        mAdapter = new HomePageAdapter(null);
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HomePageItemClickListener());
        request();

        initRefresh();
    }

    private void initRefresh() {
        ConstraintLayout rootLayout = fragmentProvider.getMineView().findViewById(R.id.root_home_layout);
        fixContent(rootLayout, fragmentProvider.getResources());

        refreshLayout = fragmentProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mRv.getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });
    }

    private void request() {
        showLoading("获取首页信息...");
        mModel.obtainHomePageInfo();
        mModel.obtainInfoList(1);
    }


}
