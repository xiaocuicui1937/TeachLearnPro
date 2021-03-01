package com.gnss.teachlearnpro.main.live.detail.commentlist;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.bean.CommentListBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.main.live.detail.commentlist.fragment.CommentListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentListALogic extends BaseLogic {
    private ActivityProvider mProvider;

    public CommentListALogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle("评论详情");
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.findViewById(R.id.rv_fragment_comment_list);
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
