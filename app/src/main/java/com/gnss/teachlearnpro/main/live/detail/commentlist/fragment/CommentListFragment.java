package com.gnss.teachlearnpro.main.live.detail.commentlist.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.ecommerce.common.ui.compent.MeBaseFragment;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;

/**
 * 评论详情列表
 */
public class CommentListFragment extends MeBaseFragment implements FragmentProvider {
    public static CommentListFragment newInstance() {

        Bundle args = new Bundle();

        CommentListFragment fragment = new CommentListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    public void init() {
        new CommentListLogic(this);
    }

    @Nullable
    @Override
    public View getMineView() {
        return mLayoutView;
    }
}
