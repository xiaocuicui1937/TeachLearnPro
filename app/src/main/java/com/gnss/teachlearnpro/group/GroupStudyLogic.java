package com.gnss.teachlearnpro.group;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.course.model.CourseViewModel;
import com.gnss.teachlearnpro.group.entry.GroupStudyEntryFragment;
import com.gnss.teachlearnpro.main.MainActivity;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class GroupStudyLogic extends BaseLogic {
    private static final int DEFAULT_PAGE = 5;
    private int mPageIndex = 1;//默认获取第一页
    private FragmentProvider mProvider;
    private GroupStudyAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private GroupStudyViewModel model;


    public GroupStudyLogic(FragmentProvider provider) {
        mProvider = provider;
        setTitle(provider, "小组学习", (AppCompatActivity) provider.getActivity());
        initRecyclerView();
        addRes();
    }

    @Override
    protected boolean showBack() {
        return false;
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_group_study);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new GroupStudyAdapter(R.layout.item_group_study, null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GroupStudyClickListener());
//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                90.replace(FragmentUtils.getTopShow(mProvider.getParentFragmentManager())
//                        , new GroupStudyEntryFragment(),true);
//            }
//        });
        fixContent(mProvider.getMineView().findViewById(R.id.root_group_study), mProvider.getResources());
    }

    private void addRes() {
        showLoading("获取小组学习列表...");
        MainActivity act = (MainActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(GroupStudyViewModel.class);
        initRefresh();
        model.obtainGroupStudyList(1);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();

        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainGroupStudyList(mPageIndex);
        });

        model.getGroupStudy().observe(act, s -> {
           handleLoadData(loadMoreModule,s);
        });
    }


    private void handleLoadData(BaseLoadMoreModule loadMoreModule, String res) {
        ArrayList<MultipleItemEntity> dataConvert = new GroupStudyDataConvert().setJsonData(res).convert();
        hideLoading();
        if (refreshLayout!=null){
            refreshLayout.finishRefresh(true);
        }
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(dataConvert);
        } else {
            mAdapter.addData(dataConvert);
        }
        if (ObjectUtils.isEmpty(dataConvert)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (dataConvert.size() < DEFAULT_PAGE) {
            //如果不够一页的话就停止加载
            loadMoreModule.loadMoreEnd();
            MeLog.e("more dengyu" + dataConvert.size());

        } else {
            MeLog.e("more dayu" + dataConvert.size());

            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }

    private void initRefresh() {
        LinearLayout rootLayout = mProvider.getMineView().findViewById(R.id.root_group_study);
        fixContent(rootLayout, mProvider.getResources());
        refreshLayout = mProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showLoading("获取小组学习列表...");
                mPageIndex = 1;
                model.obtainGroupStudyList(mPageIndex);
            }
        });
    }
}
