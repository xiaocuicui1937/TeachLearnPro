package com.gnss.teachlearnpro.profile.detail.favorite;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.FavoriteBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.course.detail.CourseDetailActivity;
import com.gnss.teachlearnpro.course.detail.play.CourseDetailPlayActivity;
import com.gnss.teachlearnpro.group.detail.GroupStudyDetailActivity;
import com.gnss.teachlearnpro.main.live.detail.fragment.LiveDetailFragment;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class FavoriteLogic extends BaseLogic {
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private FragmentProvider mProvider;
    private TabLayout mTabLayout;
    private FavoriteAdapter mAdapter;
    private FavoriteViewModel model;
    private RefreshLayout refreshLayout;
    private FavoriteViewModel.FavoriteType mFavoriteType = FavoriteViewModel.FavoriteType.ALL;

    public FavoriteLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(mProvider, "收藏", (AppCompatActivity) mProvider.getActivity());
        initView();
        addTabListener();
        addRes();
        toDetail();
    }

    private void addRes() {
        AppCompatActivity act = (AppCompatActivity) mProvider.getActivity();
        model = new ViewModelProvider(act).get(FavoriteViewModel.class);
        showLoading("获取收藏列表...");
        model.obtainFavoriteList(FavoriteViewModel.FavoriteType.ALL, mPageIndex);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainFavoriteList(mFavoriteType, mPageIndex);
        });

        model.getFavorite().observe(act, favoriteBean -> {
            hideLoading();
            handleLoadData(loadMoreModule, favoriteBean);
        });

    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, FavoriteBean favorite) {
        List<FavoriteBean.DataBean> data = favorite.getData();
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

    private void addTabListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                obtainList(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                obtainList(tab);
            }
        });
    }

    private void obtainList(TabLayout.Tab tab) {
        mAdapter.setNewInstance(null);
        mPageIndex = 1;
        switch (tab.getPosition()) {
            case 0:
                mFavoriteType = FavoriteViewModel.FavoriteType.ALL;
                showLoading("获取所有收藏...");
                model.obtainFavoriteList(mFavoriteType, mPageIndex);
                break;
            case 1:
                mFavoriteType = FavoriteViewModel.FavoriteType.COURSE;
                showLoading("获取课程收藏...");
                model.obtainFavoriteList(mFavoriteType, mPageIndex);
                break;
            case 2:
                mFavoriteType = FavoriteViewModel.FavoriteType.LIVE;

                showLoading("获取直播收藏...");
                model.obtainFavoriteList(mFavoriteType, mPageIndex);
                break;
            case 3:
                mFavoriteType = FavoriteViewModel.FavoriteType.GROUP;

                showLoading("获取小组学习收藏...");
                model.obtainFavoriteList(mFavoriteType, mPageIndex);
                break;
            default:
                break;
        }
    }

    private void initRecyclerView(View view) {
        RecyclerView rv = view.findViewById(R.id.rv_fragment_favorite);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new FavoriteAdapter(R.layout.item_favorite, null);
        rv.setAdapter(mAdapter);
    }

    private void initView() {
        View view = mProvider.getMineView();
        mTabLayout = view.findViewById(R.id.tl_fragment_favorite);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(mProvider.getActivity(),
                R.drawable.sp_divider_tab));
        linearLayout.setDividerPadding(20);

        initRecyclerView(view);
        initRefresh();
    }

    private void initRefresh() {
        refreshLayout = mProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showLoading("获取收藏列表...");
                mPageIndex = 1;
                model.obtainFavoriteList(mFavoriteType, mPageIndex);
            }
        });
    }

    private void toDetail() {
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FavoriteBean.DataBean data = mAdapter.getData().get(position);
            switch (data.getType()) {
                case 1:
                    //course
                    CacheMemoryUtils.getInstance().put(Contact.PlAY_URL, data.getHead_img());
                    CacheMemoryUtils.getInstance().put(Contact.TITLE, data.getTitle());
                    CacheMemoryUtils.getInstance().put(Contact.ID, data.getId());
                    ActivityUtils.startActivity(CourseDetailPlayActivity.class);
                    break;
                case 2:
                    //live
                    CacheMemoryUtils.getInstance().put(Contact.ID, data.getId());
                    FragmentUtils.replace(FragmentUtils.getTopShow(mProvider.getParentFragmentManager()),
                            LiveDetailFragment.newInstance(), true);
                    break;
                case 3:
                    //group learn
                    CacheMemoryUtils.getInstance().put(Contact.ID, data.getId());
                    ActivityUtils.startActivity(GroupStudyDetailActivity.class);
                    break;
                default:
                    break;
            }
        });
    }

}
