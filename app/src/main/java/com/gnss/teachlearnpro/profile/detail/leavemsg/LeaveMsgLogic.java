package com.gnss.teachlearnpro.profile.detail.leavemsg;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LeaveMsgBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.profile.detail.leavemsg.list.LeaveMsgListActivity;
import com.gnss.teachlearnpro.profile.detail.leavemsg.list.LeaveMsgListAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeaveMsgLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private LeaveMsgListAdapter mAdapter;
    private RefreshLayout refreshLayout;
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private CommentViewModel model;
    private TextView mTvCourse;
    private TextView mTvGroup;

    public LeaveMsgLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(mProvider, "我的留言", (AppCompatActivity) provider.getActivity());
        initView();
        initRecyclerView();
        addRes();
    }

    private void addRes() {
        FragmentActivity act = mProvider.getActivity();
        model = new ViewModelProvider(act).get(CommentViewModel.class);
        initObtainLeaveList();
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(() -> {
            model.obtainLeaveMsg(CommentViewModel.CommentType.LIVE, mPageIndex);
        });
        model.getLeaveMsg().observe(act, new Observer<LeaveMsgBean>() {
            @Override
            public void onChanged(LeaveMsgBean leaveMsgBean) {
                handleLoadData(loadMoreModule, leaveMsgBean);
            }
        });
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, LeaveMsgBean res) {
        hideLoading();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(true);
        }
        mTvCourse.setText(String.valueOf(res.getCourse_count()));
        mTvGroup.setText(String.valueOf(res.getTeam_count()));
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
            MeLog.e("more dengyu" + data.size());

        } else {
            MeLog.e("more dayu" + data.size());

            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_leave_msg);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LeaveMsgListAdapter(R.layout.item_leave_msg, null);
        rv.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_layout);
        mAdapter.setOnItemClickListener(new LeaveMsgItemCLickListener());
        initRefresh();
    }

    private void initRefresh() {
        refreshLayout = mProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mProvider.getActivity()));
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
            model.obtainLeaveMsg(CommentViewModel.CommentType.LIVE, mPageIndex);

        }
    }

    private void initView() {
        View view = mProvider.getMineView();
        mTvCourse = view.findViewById(R.id.tv_fragment_leave_msg_courses);
        View course = view.findViewById(R.id.tv_fragment_leave_msg_coursetip);
        mTvGroup = view.findViewById(R.id.tv_fragment_leave_msg_groups);
        View group = view.findViewById(R.id.tv_fragment_leave_msg_groupstip);
        ClickUtils.expandClickArea(course, SizeUtils.sp2px(20));
        ClickUtils.expandClickArea(group, SizeUtils.sp2px(20));
        ClickUtils.applyGlobalDebouncing(course, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLeaveMsgList(view, "系列课程", CommentViewModel.CommentType.COURSE);
            }
        });
        ClickUtils.applyGlobalDebouncing(group, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLeaveMsgList(view, "小组学习", CommentViewModel.CommentType.GROUP);
            }
        });
    }

    private void toLeaveMsgList(View view, String title, CommentViewModel.CommentType type) {
        Intent intent = new Intent(view.getContext(), LeaveMsgListActivity.class);
        intent.putExtra(Contact.TITLE, title);
        intent.putExtra(Contact.LEAVE_MSG_TYPE, type);
        ActivityUtils.startActivity(intent);
    }


}
