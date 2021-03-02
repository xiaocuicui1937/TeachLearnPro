package com.gnss.teachlearnpro.course;

import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.CourseBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.course.adapter.CourseAdapter;
import com.gnss.teachlearnpro.course.model.CourseViewModel;
import com.gnss.teachlearnpro.main.MainActivity;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

public class CourseLogic extends BaseLogic {
    private FragmentProvider mFragmentProvider;
    private CourseAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页

    public CourseLogic(FragmentProvider fragmentProvider) {
        mFragmentProvider = fragmentProvider;
        initView();
        addResModel();
    }

    private void initView() {
        SuperTextView stvTitle = mFragmentProvider.getMineView().findViewById(R.id.stv_common_title);
        stvTitle.setCenterString(StringUtils.getString(R.string.course));

        fixContent(mFragmentProvider.getMineView().findViewById(R.id.root_fragment_course), mFragmentProvider.getResources());
        RecyclerView rv = mFragmentProvider.getMineView().findViewById(R.id.rv_fragment_course);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        mAdapter = new CourseAdapter(R.layout.item_course, null);
        rv.setLayoutManager(manager);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CourseItemClickListener());
    }

    private void addResModel() {
        showLoading("获取课程列表...");
        MainActivity topActivity = (MainActivity) ActivityUtils.getTopActivity();
        CourseViewModel model = new ViewModelProvider(topActivity).get(CourseViewModel.class);
        model.obtainCourseList(mPageIndex = 1);

        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainCourseList(mPageIndex);
        });

        model.getCourseList().observe(mFragmentProvider.getViewLifecycleOwner(), courseBean -> {
            hideLoading();
            handleLoadData(loadMoreModule, courseBean);
        });
        initRefresh(model);
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, CourseBean courseBean) {
        List<CourseBean.DataBean> data = courseBean.getData();
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


    private void initRefresh(CourseViewModel model) {
        LinearLayout rootLayout = mFragmentProvider.getMineView().findViewById(R.id.root_fragment_course);
        fixContent(rootLayout, mFragmentProvider.getResources());

        refreshLayout = mFragmentProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mFragmentProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showLoading("获取课程列表...");
                model.obtainCourseList(mPageIndex = 1);
            }
        });
    }
}
