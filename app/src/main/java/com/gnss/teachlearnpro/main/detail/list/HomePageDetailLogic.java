package com.gnss.teachlearnpro.main.detail.list;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.HomeDetailBean;
import com.gnss.teachlearnpro.common.bean.InfoListResBean;
import com.gnss.teachlearnpro.common.bean.StudentWitnessResBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.main.detail.detail.HomeListViewModel;
import com.gnss.teachlearnpro.main.detail.list.adapter.HomePageInfoDetailAdapter;
import com.gnss.teachlearnpro.main.detail.list.adapter.HomePageStudentWitnessDetailAdapter;
import com.gnss.teachlearnpro.main.home.HomePageViewModel;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class HomePageDetailLogic extends BaseLogic {
    private static final int DEFAULT_PAGE = 5;
    private int mPageIndex = 1;//默认获取第一页
    private ActivityProvider mProvider;
    private HomePageViewModel model;
    private HomePageInfoDetailAdapter mInfoAdapter;
    private HomePageStudentWitnessDetailAdapter mStudentWitnessAdapter;
    private SmartRefreshLayout refreshLayout;

    public HomePageDetailLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle(provider.getIntent().getStringExtra(Contact.TITLE));

        initRecyclerView();
        initRefresh();
        addRequestListener();
    }

    private void addRequestListener() {
        HomePageDetailActivity activity = (HomePageDetailActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(activity).get(HomePageViewModel.class);
        obtainRequest();
        int homeDetailType = mProvider.getIntent().getIntExtra(Contact.HOME_DETAIL_TYPE, -1);
        switch (homeDetailType) {
            case ItemType.ARTICLE_TYPE:
                BaseLoadMoreModule loadMoreModuleInfo = mInfoAdapter.getLoadMoreModule();

                loadMoreModuleInfo.setOnLoadMoreListener(() -> {
                    obtainRequest();
                });
                model.getInfoList().observe(activity, infoListResBean -> handleInfoDetailRes(loadMoreModuleInfo, infoListResBean));
                break;
            case ItemType.STUDENT_WITNESS_TYPE:
                BaseLoadMoreModule loadMoreModuleStudent = mStudentWitnessAdapter.getLoadMoreModule();
                loadMoreModuleStudent.setOnLoadMoreListener(() -> {
                    obtainRequest();
                });
                model.getStudentWitnessList().observe(activity, studentWitnessResBean -> handleStudentWitness(loadMoreModuleStudent, studentWitnessResBean));

                break;
            default:
                break;
        }





    }

    private void obtainRequest() {
        mPageIndex = 1;
        int homeDetailType = mProvider.getIntent().getIntExtra(Contact.HOME_DETAIL_TYPE, -1);
        switch (homeDetailType) {
            case ItemType.ARTICLE_TYPE:
                showLoading("加载话题文章列表...");
                model.obtainInfoList(mPageIndex);
                break;
            case ItemType.STUDENT_WITNESS_TYPE:
                showLoading("加载学员见证列表...");
                model.obtainStudentWitnessList(mPageIndex);
                break;
            default:
                break;
        }
    }

    private void handleStudentWitness(BaseLoadMoreModule loadMoreModule, StudentWitnessResBean studentWitnessResBean) {
        hideLoading();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
        if (studentWitnessResBean.isSuccess()) {
            List<HomeDetailBean> detailBeans = new ArrayList<>();
            List<StudentWitnessResBean.DataBean> data = studentWitnessResBean.getData();
            int size = data.size();
            for (int i = 0; i < size; i++) {
                detailBeans.add(new HomeDetailBean(data.get(i).getId(), data.get(i).getTitle(), data.get(i).getImg(), String.valueOf(i), ""));
            }

            handleLoadData(loadMoreModule, detailBeans);
        } else {
            ToastUtils.showShort(studentWitnessResBean.getMsg());
        }

    }

    private void handleInfoDetailRes(BaseLoadMoreModule loadMoreModule, InfoListResBean infoListResBean) {
        hideLoading();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
        if (infoListResBean.isSuccess()) {
            List<HomeDetailBean> detailBeans = new ArrayList<>();
            List<InfoListResBean.DataBean> data = infoListResBean.getData();
            int size = data.size();
            for (int i = 0; i < size; i++) {
                InfoListResBean.DataBean dataInfo = data.get(i);
                detailBeans.add(new HomeDetailBean(dataInfo.getId(), dataInfo.getTitle(), dataInfo.getHead_img(), String.valueOf(i),
                        dataInfo.getExcerpt(), String.valueOf(dataInfo.getRead_number()), String.valueOf(dataInfo.getCollect_number()),
                        dataInfo.getAuthor()));
            }
            handleLoadData(loadMoreModule, detailBeans);
        } else {
            ToastUtils.showShort(infoListResBean.getMsg());
        }
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, List<HomeDetailBean> data) {

        int homeDetailType = mProvider.getIntent().getIntExtra(Contact.HOME_DETAIL_TYPE, -1);
        if (ItemType.ARTICLE_TYPE == homeDetailType) {

            if (mPageIndex == 1) {
                mInfoAdapter.setNewInstance(data);
            } else if (ObjectUtils.isNotEmpty(data)) {
                mInfoAdapter.addData(data);
            }
        } else {
            if (mPageIndex == 1) {
                mStudentWitnessAdapter.setNewInstance(data);
            } else if (ObjectUtils.isNotEmpty(data)) {
                mStudentWitnessAdapter.addData(data);
            }

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
        RecyclerView rv = mProvider.findViewById(R.id.rv_activity_home_page_detail);
        int homeDetailType = mProvider.getIntent().getIntExtra(Contact.HOME_DETAIL_TYPE, -1);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);

        switch (homeDetailType) {
            case ItemType.ARTICLE_TYPE:
                createInfo(rv);
                break;
            case ItemType.STUDENT_WITNESS_TYPE:
                createStudentWitness(rv);
                break;
            default:
                break;
        }
    }

    private void createStudentWitness(RecyclerView rv) {
        showLoading("加载学员见证列表....");
        mStudentWitnessAdapter = new HomePageStudentWitnessDetailAdapter(R.layout.item_student_witness_detail, null);
        rv.setAdapter(mStudentWitnessAdapter);

        mStudentWitnessAdapter.setOnItemClickListener(new HomePageDetailClickListener(HomeListViewModel.HomeListType.STUDENT_WITNESS));


    }

    private void createInfo(RecyclerView rv) {
        showLoading("加载话题文章列表....");
        mInfoAdapter = new HomePageInfoDetailAdapter(R.layout.item_info_detail, null);
        rv.setAdapter(mInfoAdapter);

        mInfoAdapter.setOnItemClickListener(new HomePageDetailClickListener(HomeListViewModel.HomeListType.INFO));
    }

    private void initRefresh() {

        refreshLayout = mProvider.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(refreshLayout.getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            obtainRequest();
        });
    }
}
