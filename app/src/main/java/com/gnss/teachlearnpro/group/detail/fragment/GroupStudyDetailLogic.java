package com.gnss.teachlearnpro.group.detail.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.bean.GroupStudyDetailBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.ui.WriteLeaveMessageCustomView;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.group.detail.GroupStudyDetailActivity;
import com.gnss.teachlearnpro.main.live.detail.fragment.LiveDetailAdapter;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

public class GroupStudyDetailLogic extends BaseLogic implements View.OnClickListener {
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private boolean isLookAll = true;
    private FragmentProvider mProvider;
    private TextView mTvTitle, mTvDate, mTvTime, mTvLocation, mTvGroup, mTvInput;
    private ImageView mIvHead;
    private String details;
    private LiveDetailAdapter mAdapter;
    private CommentViewModel commentModel;
    private ImageView mIvHeart;


    public GroupStudyDetailLogic(FragmentProvider provider) {
        this.mProvider = provider;
        setTitle(provider, "小组学习", (AppCompatActivity) provider.getActivity());
        initView();
        initRecyclerView();
        addRequestResListener();
    }

    private void addRequestResListener() {
        GroupStudyDetailActivity act = (GroupStudyDetailActivity) ActivityUtils.getTopActivity();
        GroupStudyDetailViewModel model = new ViewModelProvider(act).get(GroupStudyDetailViewModel.class);
        showLoading("获取小组详情...");
        String id = CacheMemoryUtils.getInstance().get(Contact.ID) + "";
        model.obtainGroupStudyDetail(id);
        model.getGroupDetail().observe(act, dataBean -> {
            handleDetail(act, dataBean);
        });

        commentModel = new ViewModelProvider(act).get(CommentViewModel.class);
        commentModel.obtainCommentList(CommentViewModel.CommentType.GROUP, id, 1, isLookAll);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();

        commentModel.getCommentList().observe(act, dataBean -> {
            hideLoading();
            handleLoadData(loadMoreModule, dataBean);
        });

        loadMoreModule.setOnLoadMoreListener(() -> {
            commentModel.obtainCommentList(CommentViewModel.CommentType.GROUP, id, mPageIndex, isLookAll);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_item_live_detail_thumbs) {
                addThumbs(position, act, view);
            } else if (view instanceof Switch) {
                switchMineOrAll(id, (Switch) view);
            }


        });


        commentModel.getCollect().observe(act, aBoolean -> {
            hideLoading();
            mIvHeart.setImageResource(aBoolean ? R.drawable.ic_heart_tint : R.drawable.ic_heart);
        });
    }

    private void switchMineOrAll(String id, Switch view) {
        if (view != null){
            mPageIndex = 1;
            Switch switchView = (Switch) view;
            isLookAll = !switchView.isChecked();
            commentModel.obtainCommentList(CommentViewModel.CommentType.GROUP, id, mPageIndex, isLookAll);
        }
    }

    private void addThumbs(int position, AppCompatActivity act, View view) {
        MultipleItemEntity entity = mAdapter.getData().get(position);
        int commonId = entity.getField(Contact.ID);
        commentModel.addRecording(CommentViewModel.CommentType.GROUP, String.valueOf(commonId));
        commentModel.getAddRecording().observe(act, aBoolean -> {
            TextView tvThumbs = (TextView) view;
            tvThumbs.setCompoundDrawablesWithIntrinsicBounds(aBoolean ? R.drawable.ic_thumb_tint : R.drawable.ic_thumb, 0, 0
                    , 0);
        });
    }



    private void handleLoadData(BaseLoadMoreModule loadMoreModule, CommentBean dataBean) {
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(getDatas(dataBean.getData(), true));
        } else {
            mAdapter.addData(getDatas(dataBean.getData(), false));
        }
        MeLog.e("总数：" + dataBean.getCount());
        List<CommentBean.DataBean> dataRes = dataBean.getData();
        if (ObjectUtils.isEmpty(dataRes)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (dataRes.size() < DEFAULT_PAGE) {
            //如果不够一页的话就停止加载
            loadMoreModule.loadMoreEnd();
            MeLog.e("more dengyu" + dataRes.size());

        } else {
            MeLog.e("more dayu" + dataRes.size());

            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }

    private List<MultipleItemEntity> getDatas(List<CommentBean.DataBean> dataBeans, boolean isLoadHead) {
        List<MultipleItemEntity> datas = new ArrayList<>();
        if (isLoadHead) {
            MultipleItemEntity liveDetail = MultipleItemEntity.builder()
                    .setField(ItemType.TYPE, ItemType.HEAD_TYPE)
                    .setField(Contact.TITLE, details)
                    .build();
            datas.add(liveDetail);
        }
        if (dataBeans == null) {
            return datas;
        }
        for (CommentBean.DataBean param : dataBeans) {
            MultipleItemEntity comment = MultipleItemEntity.builder()
                    .setField(ItemType.TYPE, ItemType.COMMENT_TYPE)
                    .setField(Contact.CONTENT_SUB_TITLE, param)
                    .setField(Contact.ID,param.getCommon_id())
                    .build();
            datas.add(comment);
        }

        return datas;
    }

    private void handleDetail(GroupStudyDetailActivity act, GroupStudyDetailBean.DataBean dataBean) {
        hideLoading();
        if (dataBean == null) {
            return;
        }
        mTvTitle.setText(dataBean.getTitle());
        mTvDate.setText(dataBean.getTime());
        mTvTime.setText(dataBean.getTime_start_ming() + "-" + dataBean.getTime_end_ming());
        mTvLocation.setText("Zoom:" + dataBean.getRoom_id() + ",密码:" + dataBean.getPassword());
        mTvGroup.setText(dataBean.getTeacher_name());
        Glide.with(act).load(dataBean.getImg()).into(mIvHead);
        details = dataBean.getDetails();
        mIvHeart.setImageResource(dataBean.getCollect_number() != 0 ? R.drawable.ic_heart_tint : R.drawable.ic_heart);
    }

    private void initView() {
        View mineView = mProvider.getMineView();
        mTvTitle = mineView.findViewById(R.id.tv_fragment_group_study_title);
        mTvDate = mineView.findViewById(R.id.tv_fragment_group_study_date);
        mTvTime = mineView.findViewById(R.id.tv_fragment_group_study_time);
        mTvLocation = mineView.findViewById(R.id.tv_fragment_group_study_location);
        mIvHead = mineView.findViewById(R.id.sgp_top_bg);

        mTvGroup = mineView.findViewById(R.id.tv_fragment_group_study_group);
        mTvInput = mineView.findViewById(R.id.tv_fragment_group_detail_input);
        mIvHeart = mineView.findViewById(R.id.iv_fragment_group_study_favorite);

        mineView.findViewById(R.id.tv_fragment_group_detail_launchzoom).setOnClickListener(this);
        mineView.findViewById(R.id.tv_fragment_group_detail_share).setOnClickListener(this);
        mineView.findViewById(R.id.iv_fragment_group_study_service).setOnClickListener(this);
        mineView.findViewById(R.id.tv_fragment_group_detail_input).setOnClickListener(this);
        mIvHeart.setOnClickListener(this);
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_group_study_detail);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveDetailAdapter(null);
        rv.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
//            ActivityUtils.startActivity(CommentListActivity.class);
//        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fragment_group_detail_launchzoom:
                launchZoom();
                break;
            case R.id.tv_fragment_group_detail_share:
                share();
                break;
            case R.id.iv_fragment_group_study_service:
                launchService();
                break;
            case R.id.iv_fragment_group_study_favorite:
                startFavorite();
                break;
            case R.id.tv_fragment_group_detail_input:
                writeLeaveMsg();
                break;
            default:
                break;
        }
    }

    private void writeLeaveMsg() {
        new XPopup.Builder(mTvLocation.getContext())
                .hasShadowBg(false)
                .asCustom(new WriteLeaveMessageCustomView(mTvLocation.getContext(), CommentViewModel.CommentType.GROUP,
                        CacheMemoryUtils.getInstance().get(Contact.ID) + "", 1))
                .show();
    }

    private void startFavorite() {
        showLoading("加载...");
        commentModel.setCollectStatus(CommentViewModel.CommentType.GROUP, CacheMemoryUtils.getInstance().get(Contact.ID) + "");
    }

    private void launchService() {
        //TODO
    }

    private void share() {

    }

    private void launchZoom() {

    }

    public void destroyWebView() {
        mAdapter.destroyWebView();
    }
}
