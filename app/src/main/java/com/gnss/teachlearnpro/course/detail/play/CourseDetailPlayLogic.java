package com.gnss.teachlearnpro.course.detail.play;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.WriteLeaveMessageCustomView;
import com.gnss.teachlearnpro.common.video.CommonVideoPlayerView;
import com.gnss.teachlearnpro.common.video.PlayerManager;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.main.live.detail.fragment.LiveDetailAdapter;
import com.lxj.xpopup.XPopup;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CourseDetailPlayLogic extends BaseLogic implements View.OnClickListener {
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private boolean isLookAll = true;
    private ActivityProvider mProvider;
    private TextView mTvInput;
    private LiveDetailAdapter mAdapter;
    private CommentViewModel commentModel;
    private ImageView mIvHeart;
    private PlayerManager mPlayerManager;


    public CourseDetailPlayLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle(CacheMemoryUtils.getInstance().get(Contact.TITLE));
        initView();
        initRecyclerView();
        addRequestResListener();
    }

    private void addRequestResListener() {
        CourseDetailPlayActivity act = (CourseDetailPlayActivity) ActivityUtils.getTopActivity();
        String id = CacheMemoryUtils.getInstance().get(Contact.ID) + "";

        commentModel = new ViewModelProvider(act).get(CommentViewModel.class);
        commentModel.obtainCommentList(CommentViewModel.CommentType.COURSE, id, mPageIndex, isLookAll);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        commentModel.getCommentList().observe(act, dataBean -> {
            hideLoading();
            handleLoadData(loadMoreModule, dataBean);
        });

        loadMoreModule.setOnLoadMoreListener(() -> {
            commentModel.obtainCommentList(CommentViewModel.CommentType.COURSE, id, mPageIndex, isLookAll);
        });

//        loadMoreModule.setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
//        loadMoreModule.setEnableLoadMoreIfNotFullPage(false);
        //点赞，切换自己留言还是全部
        AtomicReference<TextView> thumbsAtom = new AtomicReference<>();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_item_live_detail_thumbs) {
                thumbsAtom.set((TextView) view);
                addThumbs(position);
            } else if (view instanceof Switch) {
                switchMineOrAll(id, (Switch) view);
            }
        });
        tothumbRes(act, thumbsAtom);
        //监听收藏回调
        commentModel.getCollect().observe(act, aBoolean -> {
            hideLoading();
            mIvHeart.setImageResource(aBoolean ? R.drawable.ic_heart_tint : R.drawable.ic_heart);
        });
    }

    private void addThumbs(int position) {
        MultipleItemEntity entity = mAdapter.getData().get(position);
        int commonId = entity.getField(Contact.ID);
        commentModel.addRecording(String.valueOf(commonId));
    }

    private void tothumbRes(CourseDetailPlayActivity act, AtomicReference<TextView> thumbsAtom) {
        commentModel.getAddRecording().observe(act, aBoolean -> {
            TextView thumbsTv = thumbsAtom.get();
            int recordingCount = Integer.parseInt(thumbsTv.getText().toString());
            //点赞成功点赞数会在原来的基础上加1，取消点赞在原来的基础上减1
            if (aBoolean) {
                recordingCount++;
            } else if (recordingCount > 0) {
                recordingCount--;
            }
            thumbsTv.setText(String.valueOf(recordingCount));
            thumbsTv.setCompoundDrawablesWithIntrinsicBounds(aBoolean ? R.drawable.ic_thumb_tint : R.drawable.ic_thumb, 0, 0, 0);
        });
    }

    private void switchMineOrAll(String id, Switch view) {
        Switch switchView = view;
        mPageIndex = 1;
        isLookAll = !switchView.isChecked();
        commentModel.obtainCommentList(CommentViewModel.CommentType.COURSE, id, mPageIndex, isLookAll);
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, CommentBean dataBean) {
        if (dataBean == null) {
            return;
        }
        List<CommentBean.DataBean> dataRes = dataBean.getData();
        if (ObjectUtils.isEmpty(dataRes)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(getDatas(dataRes));
        } else {
            mAdapter.addData(getDatas(dataRes));
        }
        MeLog.e("总数：" + dataBean.getCount());

        if (dataRes.size() < DEFAULT_PAGE) {
            //如果不够一页的话就停止加载
            loadMoreModule.loadMoreEnd();
            return;
        } else {
            loadMoreModule.loadMoreComplete();
        }
        mPageIndex++;
    }

    private List<MultipleItemEntity> getDatas(List<CommentBean.DataBean> dataBeans) {
        List<MultipleItemEntity> datas = new ArrayList<>();

        if (dataBeans == null) {
            return datas;
        }
        for (CommentBean.DataBean param : dataBeans) {
            MultipleItemEntity comment = MultipleItemEntity.builder()
                    .setField(ItemType.TYPE, ItemType.COMMENT_TYPE)
                    .setField(Contact.CONTENT_SUB_TITLE, param)
                    .setField(Contact.ID, param.getId())
                    .build();
            datas.add(comment);
        }

        return datas;
    }


    private void initView() {
        CommonVideoPlayerView player = mProvider.findViewById(R.id.sgp_top_bg);
        mPlayerManager = new PlayerManager(player);
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        mPlayerManager.init(instance.get(Contact.PlAY_URL), null, instance.get(Contact.TITLE));
        mTvInput = mProvider.findViewById(R.id.tv_activity_group_detail_input);
        mIvHeart = mProvider.findViewById(R.id.iv_activity_course_favorite);
        boolean isCollect = instance.get(Contact.IS_COLLECT);
        mIvHeart.setImageResource(isCollect?R.drawable.ic_heart_tint:R.drawable.ic_heart);

        mTvInput.setOnClickListener(this);
        mIvHeart.setOnClickListener(this);
    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.findViewById(R.id.rv_activity_course_detail);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveDetailAdapter(null);
        rv.setAdapter(mAdapter);

//       mAdapter.setOnItemClickListener((adapter1, view, position) -> {
//            ActivityUtils.startActivity(CommentListActivity.class);
//        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_activity_course_service:
                launchService();
                break;
            case R.id.iv_activity_course_favorite:
                startFavorite();
                break;
            case R.id.tv_activity_group_detail_input:
                writeLeaveMsg();
                break;
            default:
                break;
        }
    }

    private void writeLeaveMsg() {
        new XPopup.Builder(mTvInput.getContext())
                .hasShadowBg(false)
                .asCustom(new WriteLeaveMessageCustomView(mTvInput.getContext(), CommentViewModel.CommentType.COURSE,
                        CacheMemoryUtils.getInstance().get(Contact.ID) + "", mPageIndex,isLookAll))
                .show();
    }

    private void startFavorite() {
        showLoading("加载...");
        commentModel.setCollectStatus(CommentViewModel.CommentType.COURSE, CacheMemoryUtils.getInstance().get(Contact.ID) + "");
    }

    private void launchService() {
        //TODO
    }

    public void destroyWebView() {
        mAdapter.destroyWebView();
    }

    public void onVideoPause() {
        mPlayerManager.onVideoPause();
    }

    public void onVideoResume() {
        mPlayerManager.onVideoResume();
    }

    public void releaseAllVideos() {
        mPlayerManager.releaseAllVideos();
    }

    public void onBackPressed() {
        mPlayerManager.onBackPressed();
    }
}
