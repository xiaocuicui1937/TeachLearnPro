package com.gnss.teachlearnpro.main.detail.detail.info;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.course.detail.list.HtmlLoadManager;
import com.gnss.teachlearnpro.main.detail.detail.HomeListViewModel;

public class InfoDetailLogic extends BaseLogic {
    private ActivityProvider mProvider;
    private TextView mTvTitle, mTvCounts;
    private ImageView mIv;
    private HtmlLoadManager mHtmlLoadManager;

    public InfoDetailLogic(ActivityProvider provider) {
        this.mProvider = provider;

        setTitle(provider.getIntent().getStringExtra(Contact.TITLE));
        initView();
        addRequestListener();
    }

    private void addRequestListener() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        HomeListViewModel model = new ViewModelProvider(act).get(HomeListViewModel.class);
        int id = mProvider.getIntent().getIntExtra(Contact.ID, -1);
        showLoading("获取话题文章详情");
        model.obtainDetail(HomeListViewModel.HomeListType.INFO, String.valueOf(id));
        model.getList().observe(act, homeDetailBean -> {
            hideLoading();
            mTvTitle.setText(homeDetailBean.title);
            //+ "   收藏数:" + homeDetailBean.collectCount
            mTvCounts.setText("阅读数:" + homeDetailBean.readCount );
            mHtmlLoadManager.loadHtmlCode(homeDetailBean.content);
            Glide.with(mIv.getContext()).load(homeDetailBean.logoUrl).into(mIv);
        });
    }

    private void initView() {
        mHtmlLoadManager = new HtmlLoadManager();
        mTvTitle = mProvider.findViewById(R.id.tv_list_info_detail);
        mTvCounts = mProvider.findViewById(R.id.tv_list_info_counts);
        FrameLayout layout = mProvider.findViewById(R.id.fl_list_info_content);
        mIv = mProvider.findViewById(R.id.iv_activity_list_info);
        mHtmlLoadManager.initWebView(layout);
    }

    public void destroyWebView() {
        if (mHtmlLoadManager!=null){
            mHtmlLoadManager.destroyWebView();
        }
    }
}
