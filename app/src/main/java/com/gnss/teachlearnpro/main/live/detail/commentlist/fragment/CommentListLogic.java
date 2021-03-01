package com.gnss.teachlearnpro.main.live.detail.commentlist.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.CommentListBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

import java.util.ArrayList;
import java.util.List;

public class CommentListLogic extends BaseLogic {
    private FragmentProvider mProvider;

    public CommentListLogic(FragmentProvider provider) {
        mProvider = provider;
        setTitle(provider, "评论详情", (AppCompatActivity) ActivityUtils.getTopActivity());
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_comment_list);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        CommentListAdapter adapter = new CommentListAdapter(R.layout.item_comment_detail, getDatas());
        rv.setAdapter(adapter);
    }

    private List<CommentListBean> getDatas() {
        List<CommentListBean> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CommentListBean comments = new CommentListBean("1", "1", "1", "1");
            datas.add(comments);
        }
        return datas;
    }
}
