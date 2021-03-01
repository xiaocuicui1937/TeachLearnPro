package com.gnss.teachlearnpro.course;

import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.bean.CourseBean;
import com.gnss.teachlearnpro.course.adapter.CourseAdapter;
import com.gnss.teachlearnpro.course.model.CourseViewModel;
import com.gnss.teachlearnpro.main.MainActivity;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class CourseLogic extends BaseLogic {
    private FragmentProvider mFragmentProvider;
    private CourseAdapter mAdapter;
    private RefreshLayout refreshLayout;

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
        model.obtainCourseList();
        model.getCourseList().observe(mFragmentProvider.getViewLifecycleOwner(), courseBean -> {
            hideLoading();
            refreshLayout.finishRefresh(true);

            if (courseBean==null)return;
            mAdapter.setNewInstance(courseBean.getData());
        });
        initRefresh(model);
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
                model.obtainCourseList();
            }
        });
    }
}
