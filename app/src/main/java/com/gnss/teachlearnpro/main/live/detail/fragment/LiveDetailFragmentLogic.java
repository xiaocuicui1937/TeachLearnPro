package com.gnss.teachlearnpro.main.live.detail.fragment;

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
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.bean.LiveDetailResBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.ui.WriteLeaveMessageCustomView;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.main.live.detail.commentlist.CommentListActivity;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

public class LiveDetailFragmentLogic extends BaseLogic implements View.OnClickListener {
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private boolean isLookAll = true;
    private FragmentProvider mProvider;
    LiveDetailFragment mFragment;
    private CommentViewModel commentModel;
    private LiveDetailAdapter mAdapter;
    private String details;
    private LivePlayerManager mLivePlayerManager;
    private TextView mTvInput;
    private ImageView mIvHeart;

    public LiveDetailFragmentLogic(FragmentProvider provider, LiveDetailFragment fragment) {
        this.mProvider = provider;
        this.mFragment = fragment;
        setTitle(provider, CacheMemoryUtils.getInstance().get(Contact.TITLE), (AppCompatActivity) ActivityUtils.getTopActivity());
        initView();
        initRecyclerView();
        addRequestResListener();
//        sendComment();
    }

    private void initView() {
        View view = mProvider.getMineView();
        mLivePlayerManager = new LivePlayerManager(mProvider.getMineView().findViewById(R.id.live_top_bg));
        mTvInput = view.findViewById(R.id.tv_fragment_live_detail_input);
        mIvHeart = view.findViewById(R.id.iv_fragment_live_detail_favorite);
        mTvInput.setOnClickListener(this);
        mIvHeart.setOnClickListener(this);
    }

//    private void sendComment() {
//        TextView inputTv = mProvider.getMineView().findViewById(R.id.tv_fragment_live_detail_input);
//        inputTv.setOnClickListener(view -> {
//            new XPopup.Builder(inputTv.getContext())
//                    .hasShadowBg(false)
//                    .asCustom(new WriteLeaveMessageCustomView(inputTv.getContext()))
//                    .show();
//        });
//    }

    private void initRecyclerView() {
        RecyclerView rv = mProvider.getMineView().findViewById(R.id.rv_fragment_live_detail);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveDetailAdapter(null);
        rv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
            ActivityUtils.startActivity(CommentListActivity.class);
        });

    }

    private void addRequestResListener() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        LiveDetailViewModel model = new ViewModelProvider(act).get(LiveDetailViewModel.class);
        showLoading("获取直播详情...");
        String id = CacheMemoryUtils.getInstance().get(Contact.ID) + "";
        model.obtainLiveDetail(id);
        model.getLiveDetail().observe(act, dataBean -> {
            handleDetail(dataBean);
        });

        commentModel = new ViewModelProvider(act).get(CommentViewModel.class);
        commentModel.obtainCommentList(CommentViewModel.CommentType.LIVE, id, 1, isLookAll);
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        commentModel.getCommentList().observe(act, dataBean -> {
            hideLoading();
            handleLoadData(loadMoreModule, dataBean);
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
            switchHeartStatus(aBoolean);
        });
    }

    private void switchHeartStatus(Boolean isSelected) {
        mIvHeart.setImageResource(isSelected ? R.drawable.ic_heart_tint : R.drawable.ic_heart);
    }


    private void addThumbs(int position, AppCompatActivity act, View view) {

        MultipleItemEntity entity = mAdapter.getData().get(position);
        int commonId = entity.getField(Contact.ID);
        commentModel.addRecording(CommentViewModel.CommentType.LIVE, String.valueOf(commonId));
        commentModel.getAddRecording().observe(act, aBoolean -> {
            TextView tvThumbs = (TextView) view;
            tvThumbs.setCompoundDrawablesWithIntrinsicBounds(aBoolean ? R.drawable.ic_thumb_tint : R.drawable.ic_thumb, 0, 0
                    , 0);
        });
    }

    private void switchMineOrAll(String id, Switch view) {
        Switch switchView = (Switch) view;
        mPageIndex = 1;
        isLookAll = !switchView.isChecked();
        commentModel.obtainCommentList(CommentViewModel.CommentType.LIVE, id, mPageIndex, isLookAll);
    }

    private void handleLoadData(BaseLoadMoreModule loadMoreModule, CommentBean dataBean) {
        List<CommentBean.DataBean> dataRes = dataBean.getData();
        if (ObjectUtils.isEmpty(dataRes)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(getDatas(dataRes, true));
        } else {
            mAdapter.addData(getDatas(dataRes, false));
        }
        MeLog.e("总数：" + dataBean.getCount());

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

    private void handleDetail(LiveDetailResBean dataBean) {
        hideLoading();
        if (dataBean == null) {
            return;
        }
        if (dataBean.isSuccess()) {
            LiveDetailResBean.DataBean data = dataBean.getData();
            switchHeartStatus(data.isCollect());
            details = data.getDetails();
            mLivePlayerManager.playLive(data.getPlay_url_flv());
            MeLog.e("拉流地址:" + data.getPlay_url_flv());
            mAdapter.setNewInstance(getDatas(null, true));
        } else {
            ToastUtils.showShort(dataBean.getMsg());
        }
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
                    .setField(Contact.ID, param.getCommon_id())
                    .build();
            datas.add(comment);
        }

        return datas;
    }


    public void resumeLive() {
        // 继续
        mLivePlayerManager.resumeLive();
    }

    public void pauseLive() {
        // 暂停
        mLivePlayerManager.pauseLive();
    }

    /**
     * 结束直播
     */
    public void stopLive() {
        mLivePlayerManager.stopLive();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fragment_live_detail_favorite:
                startFavorite();
                break;
            case R.id.tv_fragment_live_detail_input:
                writeLeaveMsg();
                break;
            default:
                break;
        }
    }

    private void writeLeaveMsg() {
        new XPopup.Builder(mTvInput.getContext())
                .hasShadowBg(false)
                .asCustom(new WriteLeaveMessageCustomView(mTvInput.getContext(), CommentViewModel.CommentType.LIVE,
                        CacheMemoryUtils.getInstance().get(Contact.ID) + "", 1))
                .show();
    }

    private void startFavorite() {
        showLoading("加载...");
        commentModel.setCollectStatus(CommentViewModel.CommentType.LIVE,
                CacheMemoryUtils.getInstance().get(Contact.ID) + "");
    }

}
