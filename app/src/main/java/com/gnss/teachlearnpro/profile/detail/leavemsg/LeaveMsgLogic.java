package com.gnss.teachlearnpro.profile.detail.leavemsg;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.profile.ProfileViewModel;
import com.gnss.teachlearnpro.profile.detail.leavemsg.list.LeaveMsgListActivity;
import com.gnss.teachlearnpro.profile.detail.leavemsg.list.LeaveMsgListAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

public class LeaveMsgLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private LeaveMsgListAdapter mAdapter;
    private RefreshLayout refreshLayout;

    public LeaveMsgLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(mProvider, "我的留言", (AppCompatActivity) provider.getActivity());
        initView();
        initRecyclerView();
        addRes();
    }

    private void addRes() {
        ProfileViewModel model = new ViewModelProvider(mProvider.getActivity()).get(ProfileViewModel.class);
        model.obtainLeaveMsg();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_leave_msg);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LeaveMsgListAdapter(R.layout.item_leave_msg, null);
        rv.setAdapter(mAdapter);
        initRefresh();
    }

    private void initRefresh() {
        refreshLayout = mProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {

            }
        });
    }

    private void initView() {
        View view = mProvider.getMineView();
        View course = view.findViewById(R.id.tv_fragment_leave_msg_coursetip);
        View group = view.findViewById(R.id.tv_fragment_leave_msg_groupstip);
        ClickUtils.expandClickArea(course, SizeUtils.sp2px(20));
        ClickUtils.expandClickArea(group, SizeUtils.sp2px(20));
        ClickUtils.applyGlobalDebouncing(course, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(LeaveMsgListActivity.class);
            }
        });
        ClickUtils.applyGlobalDebouncing(group, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(LeaveMsgListActivity.class);
            }
        });
    }


}
