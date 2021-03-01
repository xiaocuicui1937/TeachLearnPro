package com.gnss.teachlearnpro.main.detail.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;

public class HomeListDetailActivity extends BaseActivity implements ActivityProvider {

    private HomeListDetailLogic mLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeListViewModel.HomeListType type = (HomeListViewModel.HomeListType) getIntent().getSerializableExtra(Contact.HOME_DETAIL_TYPE);
        if (type == HomeListViewModel.HomeListType.INFO) {
            setContentView(R.layout.activity_home_list_info_detail);
        } else if (type == HomeListViewModel.HomeListType.STUDENT_WITNESS) {
            setContentView(R.layout.activity_home_list_student_detail);
        }
        mLogic = new HomeListDetailLogic(this, type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLogic.destroyWebView();
    }
}
