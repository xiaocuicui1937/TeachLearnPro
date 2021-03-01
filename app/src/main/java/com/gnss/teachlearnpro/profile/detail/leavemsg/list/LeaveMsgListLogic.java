package com.gnss.teachlearnpro.profile.detail.leavemsg.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.LeaveMsgListBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.profile.detail.leavemsg.detail.LeaveMsgDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class LeaveMsgListLogic extends BaseLogic {
    private ActivityProvider mProvider;

    public LeaveMsgListLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle("系列课程");
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.findViewById(R.id.rv_activity_leave_msg_list);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        LeaveMsgListAdapter adapter = new LeaveMsgListAdapter(R.layout.item_leave_msg, getData());
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ActivityUtils.startActivity(LeaveMsgDetailActivity.class);
            }
        });
    }

    private List<LeaveMsgListBean> getData() {
        List<LeaveMsgListBean> leaveMsgs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            LeaveMsgListBean leave = new LeaveMsgListBean("1", "1", "1");
            leaveMsgs.add(leave);
        }
        return leaveMsgs;
    }


}
