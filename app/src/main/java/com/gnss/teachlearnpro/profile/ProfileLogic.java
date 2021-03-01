package com.gnss.teachlearnpro.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.ProfileBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.main.MainActivity;
import com.gnss.teachlearnpro.profile.detail.ProfileDetailActivity;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class ProfileLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private ImageView ivAvator;
    private TextView tvNick;
    private TextView tvLocation;
    private TextView tvPhoneNum;
    private TextView tvLiveCounts;
    private TextView tvCourseCounts;
    private ProfileViewModel model;
    private RefreshLayout refreshLayout;
    RoundedCorners roundedCorners = new RoundedCorners(50);


    public ProfileLogic(FragmentProvider provider) {
        this.mProvider = provider;
        initRefresh();
        initView();
        addRequest();
    }

    private void addRequest() {
        MainActivity act = (MainActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(ProfileViewModel.class);
        model.getProfile().observe(act, profileBean -> parseProfile(profileBean));
        model.getLogout().observe(act, baseResBean -> {
            if (baseResBean.isSuccess()) {
                AppUtils.relaunchApp();
            } else {
                ToastUtils.showShort(baseResBean.getMsg());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void parseProfile(ProfileBean profileBean) {
        hideLoading();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
        }
        if (profileBean == null) return;
        if (!profileBean.isSuccess()) {
            ToastUtils.showShort(profileBean.getMsg());
            return;
        }
        ProfileBean.DataBean data = profileBean.getData();
        CacheDiskStaticUtils.put(Contact.PROFILE, GsonUtils.toJson(data));
        if (data != null) {
            Glide.with(ivAvator.getContext()).applyDefaultRequestOptions(RequestOptions.bitmapTransform(roundedCorners)).load(data.getAvatar()).into(ivAvator);
            tvNick.setText(data.getNickname());
//            tvLocation.setText(data.getProvince_name() + data.getCity_name());
            tvLocation.setText(data.getSex() == 1 ? "男" : "女");
            tvPhoneNum.setVisibility(View.GONE);
            tvLiveCounts.setText(String.valueOf(data.getLive_count()));
            tvCourseCounts.setText(String.valueOf(data.getCourse_count()));
        }

    }

    private void initView() {
        View view = mProvider.getMineView();
        ivAvator = view.findViewById(R.id.iv_fragment_profile_avator);
        tvNick = view.findViewById(R.id.tv_fragment_profile_nick);
        tvLocation = view.findViewById(R.id.tv_fragment_profile_location);
        tvPhoneNum = view.findViewById(R.id.tv_fragment_profile_phonenum);
        tvLiveCounts = view.findViewById(R.id.tv_fragment_leave_msg_courses);
        tvCourseCounts = view.findViewById(R.id.tv_fragment_leave_msg_groups);

        view.findViewById(R.id.cl_top).setOnClickListener(view1 -> {
            toProfileDetail(Contact.PROFILE_INFO_TYPE);
        });

        tvLiveCounts.setOnClickListener(view12 -> {
            toProfileDetail(Contact.PROFILE_LIVE_RECORD_TYPE);
        });
        view.findViewById(R.id.tv_fragment_leave_msg_coursetip).setOnClickListener(view13 -> {
            toProfileDetail(Contact.PROFILE_LIVE_RECORD_TYPE);
        });

        tvCourseCounts.setOnClickListener(view12 -> {
            toProfileDetail(Contact.PROFILE_COURSE_RECORD_TYPE);
        });
        view.findViewById(R.id.tv_fragment_leave_msg_groupstip).setOnClickListener(view13 -> {
            toProfileDetail(Contact.PROFILE_COURSE_RECORD_TYPE);
        });
        view.findViewById(R.id.tv_fragment_profile_setting).setOnClickListener(view14 -> {
            toProfileDetail(Contact.PROFILE_SETTING_TYPE);
        });

        view.findViewById(R.id.tv_fragment_profile_share).setOnClickListener(view15 -> {
            toProfileDetail(Contact.PROFILE_SHARE_APP_TYPE);
        });

        view.findViewById(R.id.tv_fragment_profile_about_me).setOnClickListener(view15 -> {
            toProfileDetail(Contact.PROFILE_ABOUT_ME_TYPE);
        });

        view.findViewById(R.id.tv_fragment_profile_logout).setOnClickListener(view15 -> {
            model.logout();
        });

        view.findViewById(R.id.tv_fragment_profile_message).setOnClickListener(view15 -> {
            toProfileDetail(Contact.PROFILE_LEAVE_MSG_TYPE);
        });

        view.findViewById(R.id.tv_fragment_profile_collect).setOnClickListener(view15 -> {
            toProfileDetail(Contact.PROFILE_FAVORITE_TYPE);
        });

    }


    private void toProfileDetail(int type) {
        Intent intent = new Intent(mProvider.getMineView().getContext(), ProfileDetailActivity.class);
        intent.putExtra(Contact.PROFILE_DETAIL_TYPE, type);
        ActivityUtils.startActivity(intent);
    }

    private void initRefresh() {
        refreshLayout = mProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showLoading("获取个人信息");
                model.obtainProfile();
            }
        });
    }

    public void forceRefresh() {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }
}
