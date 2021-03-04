package com.gnss.teachlearnpro.course.detail.list;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CourseDetailBean;
import com.gnss.teachlearnpro.common.bean.PlayItemBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.course.detail.CourseDetailActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.ConfirmPopupView;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailListLogic extends BaseLogic {
    private FragmentProvider mProvider;
    private CourseDetailListAdapter mAdapter;
    private AppBarLayout mAppBarLayout;
    private ConstraintLayout mHeadLayout;
    private CollapsingToolbarLayout mCollapsToolbarLayout;
    private HtmlLoadManager mHtmlManager;

    public CourseDetailListLogic(FragmentProvider provider) {
        this.mProvider = provider;
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        BarUtils.setStatusBarVisibility(act, false);
        initRecyclerView();
        setToolBarReplaceActionBar();
        addRequestResListener(act);
        mHtmlManager = new HtmlLoadManager();
    }

    private void addRequestResListener(AppCompatActivity act) {
        CourseDetailViewModel model = new ViewModelProvider(act)
                .get(CourseDetailViewModel.class);
        int id = mProvider.getArguments().getInt(Contact.ID);
        showLoading("获取课程...");
        model.obtainCourseDetail(String.valueOf(id));
        model.getCourse().observe(act, course -> {
            hideLoading();
            if (course.isSuccess()) {
                parseCourse(course.getData());
            } else {
                ToastUtils.showShort(course.getMsg());
            }
        });
    }

    private void parseCourse(CourseDetailBean.DataBean dataBean) {
        hideLoading();
        View mineView = mProvider.getMineView();
        TextView tvTitle = mineView.findViewById(R.id.tv_fragment_course_detail_title_mine);
        TextView tvSubTitle = mineView.findViewById(R.id.tv_fragment_course_detail_subtitle);
        TextView tvLiveCount = mineView.findViewById(R.id.tv_fragment_course_detail_livecounts);
        TextView tvManCount = mineView.findViewById(R.id.tv_fragment_course_detail_course);
        FrameLayout flContent = mineView.findViewById(R.id.fl_fragment_course_detail_desc);
        ImageView ivCover = mineView.findViewById(R.id.iv_fragment_course_detail_list);
        ImageView ivAvatar = mineView.findViewById(R.id.iv_fragment_course_detail_avatar);
        mHtmlManager.initWebView(flContent);

        setCourseData(dataBean, tvTitle, tvSubTitle, tvLiveCount, tvManCount, ivCover, ivAvatar);
    }

    private void setCourseData(CourseDetailBean.DataBean dataBean, TextView tvTitle, TextView tvSubTitle, TextView tvLiveCount
            , TextView tvManCount, ImageView ivCover, ImageView ivAvatar) {
        tvSubTitle.setOnClickListener(view -> tipDesc(tvSubTitle.getText().toString()));
        CourseDetailBean.DataBean.CourseBean course = dataBean.getCourse();
        tvTitle.setText(course.getTitle());
        tvSubTitle.setText(course.getDesc());
        tvLiveCount.setText(course.getNew_total() + "/" + dataBean.getCourse().getTotal());
        tvManCount.setText(course.getTotal_user() + "人加入学习");
        mHtmlManager.loadHtmlCode(course.getIntro());
        setTitleToCollapsingToolbarLayout(course.getTitle());
        Glide.with(ivCover.getContext()).load(Contact.BASE_PIC_URL + course.getLogo()).into(ivCover);
        List<CourseDetailBean.DataBean.UserBean> users = dataBean.getUser();
        if (ObjectUtils.isNotEmpty(users)) {
            RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop()).diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(ivAvatar.getContext()).applyDefaultRequestOptions(options)
                    .load(users.get(0).getAvatar()).into(ivAvatar);
        }
        List<CourseDetailBean.DataBean.CatalogListBean> catalog_list = dataBean.getCatalog_list();
        mAdapter.setNewInstance(getDatas(catalog_list));
    }

    private void tipDesc(String msg) {
        ConfirmPopupView confirmPopupView = new XPopup.Builder(mProvider.getActivity())
                .asConfirm("", msg, () -> {

                });
        confirmPopupView.isHideCancel = true;
        confirmPopupView.show();
    }

    private List<PlayItemBean> getDatas(List<CourseDetailBean.DataBean.CatalogListBean> catalogList) {
        List<PlayItemBean> list = new ArrayList<>();
        for (CourseDetailBean.DataBean.CatalogListBean param : catalogList) {
            list.add(new PlayItemBean(param.getId(), param.getTitle(), param.getTime() + "|" + param.getCreate_time(), param.getUrl()));
        }
        return list;
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_course_detail_list);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new CourseDetailListAdapter(R.layout.item_course_detail_list, null);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CourseDetailItemClickListener());
    }

    /**
     * 用toolBar替换ActionBar
     */
    private void setToolBarReplaceActionBar() {
        mCollapsToolbarLayout = mProvider.getMineView().findViewById(R.id.coll_fragment_course_detail_list);
        mAppBarLayout = mProvider.getMineView().findViewById(R.id.app_bar_fragment_course_layout);
        mHeadLayout = mProvider.getMineView().findViewById(R.id.cl_fragment_course_head_layout);

        mProvider.getMineView().findViewById(R.id.iv_fragment_course_detail_list_back)
                .setOnClickListener(view -> finishFragmentToActivity((CourseDetailActivity) mAppBarLayout.getContext()));
    }

    /**
     * 使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，
     * 设置到Toolbar上则不会显示
     * finishFragmentToActivity((CourseDetailActivity) ActivityUtils.getTopActivity());
     */
    private void setTitleToCollapsingToolbarLayout(String title) {
        Toolbar toolbar = mProvider.getMineView().findViewById(R.id.toolbar_fragment_course_detail_list);
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            MeLog.e("AppBarHeight:" + verticalOffset + "\n" + (-mHeadLayout.getHeight() * 4 / 5));
            if (verticalOffset <= -mHeadLayout.getHeight() / 3) {
                toolbar.setTitle(title);
                //使用下面两个CollapsingToolbarLayout的方法设置展开透明->折叠时你想要的颜色
                mCollapsToolbarLayout.setExpandedTitleColor(mProvider.getResources().getColor(android.R.color.transparent));
                mCollapsToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
            } else {
                mCollapsToolbarLayout.setTitle("");
            }
        });

    }

    public void destroyWebView() {
        mHtmlManager.destroyWebView();
    }
}
