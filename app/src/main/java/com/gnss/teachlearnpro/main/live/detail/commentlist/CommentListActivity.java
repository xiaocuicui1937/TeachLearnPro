package com.gnss.teachlearnpro.main.live.detail.commentlist;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class CommentListActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comment_list);
        new CommentListALogic(this);
    }


}
