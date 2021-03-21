package com.gnss.teachlearnpro.profile.detail.leavemsg.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LeaveMsgBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.profile.detail.leavemsg.detail.LeaveMsgDetailActivity;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeaveMsgListLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private RefreshLayout refreshLayout;
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private CommentViewModel model;
    private LeaveMsgListAdapter mAdapter;
    private CommentViewModel.CommentType mType;

    public LeaveMsgListLogic(ActivityProvider provider) {
        this.mProvider = provider;

        setTitle(provider.getIntent().getStringExtra(Contact.TITLE));
        initRecyclerView();
        addRes();
        initRefresh();
    }

    private void addRes() {
        mType = (CommentViewModel.CommentType) mProvider.getIntent().getSerializableExtra(Contact.LEAVE_MSG_TYPE);

        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(CommentViewModel.class);
        initObtainLeaveList();
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainLeaveMsg(mType, mPageIndex);
        });
        model.getLeaveMsg().observe(act, leaveMsgBean -> handleLoadData(loadMoreModule, leaveMsgBean));
    }

    private void initRefresh() {
        refreshLayout = mProvider.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(ActivityUtils.getTopActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                initObtainLeaveList();
            }
        });
    }

    public void initObtainLeaveList() {
        mPageIndex = 1;
        if (model != null) {
            showLoading("获取留言列表...");
            model.obtainLeaveMsg(mType, mPageIndex);
        }
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, LeaveMsgBean res) {
        hideLoading();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
        if (ObjectUtils.isEmpty(res)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (res.getCount()==0){
            mAdapter.setNewInstance(null);
        }
        List<LeaveMsgBean.DataBean> data = res.getData();
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
            return;
        } else {
            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.findViewById(R.id.rv_activity_leave_msg_list);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LeaveMsgListAdapter(R.layout.item_leave_msg, null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LeaveMsgBean.DataBean dataBean = mAdapter.getData().get(position);
                CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
                instance.put(Contact.LEAVE_MSG_DETAIL, dataBean);
                instance.put(Contact.LEAVE_MSG_TYPE, mType);
                ActivityUtils.startActivity(LeaveMsgDetailActivity.class);
            }
        });
    }


}
