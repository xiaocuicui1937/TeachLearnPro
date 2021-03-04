package com.gnss.teachlearnpro.live.liveplan.detail.liveInteract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.gnss.teachlearnpro.common.ui.WriteLeaveMessageCustomView;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.live.liveplan.detail.adapter.LiveInteractInnerAdapter;
import com.gnss.teachlearnpro.live.liveplan.detail.liveInteract.reward.RewardCustomView;
import com.gnss.teachlearnpro.main.live.detail.fragment.LiveDetailViewModel;
import com.lxj.xpopup.XPopup;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiveInteractInnerLogic extends BaseLogic implements View.OnClickListener {
    private FragmentProvider mFragmentProvider;
    private LiveInteractInnerAdapter mAdapter;
    private CommentViewModel commentModel;
    private static final int DEFAULT_PAGE = 10;
    private int mPageIndex = 1;//默认获取第一页
    private boolean isLookAll = true;
    private ImageView mIvHeart;
    private RefreshLayout refreshLayout;
    private SwitchCompat mSwith;

    public LiveInteractInnerLogic(FragmentProvider fragmentProvider) {
        mFragmentProvider = fragmentProvider;
        initView();
        initRecyclerView();
        addRes();
        initRefresh();
    }

    private void initView() {
        View view = mFragmentProvider.getMineView();
        mSwith = view.findViewById(R.id.switch_vp_live_interact);
        mIvHeart = view.findViewById(R.id.iv_fragment_live_inner_interact_favorite);
        view.findViewById(R.id.tv_fragment_live_inner_interact_input).setOnClickListener(this);
        view.findViewById(R.id.iv_fragment_live_inner_interact_favorite).setOnClickListener(this);
        view.findViewById(R.id.iv_fragment_live_inner_interact_service).setOnClickListener(this);
        addSwitchListener();
    }

    private void addSwitchListener() {
        mSwith.setOnCheckedChangeListener((compoundButton, b) -> {
            mPageIndex = 1;
            isLookAll = !mSwith.isChecked();
            commentModel.obtainCommentList(CommentViewModel.CommentType.GROUP, CacheMemoryUtils.getInstance().get(Contact.ID), mPageIndex, isLookAll);
        });
    }

    private void addRes() {
        String id = CacheMemoryUtils.getInstance().get(Contact.ID);
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        commentModel = new ViewModelProvider(act).get(CommentViewModel.class);
        obtainInteract(id, 1);
        setHeartStatus(act);
        refreshAndLoadMore(id, act);

    }

    private void refreshAndLoadMore(String id, AppCompatActivity act) {
        BaseLoadMoreModule loadMoreModule = mAdapter.getLoadMoreModule();
        commentModel.getCommentList().observe(act, dataBean -> {
            hideLoading();
            refreshLayout.finishRefresh();
            handleLoadData(loadMoreModule, dataBean);
        });
        loadMoreModule.setOnLoadMoreListener(() -> {
            commentModel.obtainCommentList(CommentViewModel.CommentType.LIVE, id, mPageIndex, isLookAll);
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_item_live_detail_thumbs) {
                addThumbs(position, act, view);
            } else if (view instanceof Switch) {
                switchMineOrAll(id, (Switch) view);
            }
        });
    }

    /**
     * 设置收藏heart显示
     * 包含初始化显示 选中显示tint图标 未选中显示空心图标
     *
     * @param act AppCompatActivity
     */
    private void setHeartStatus(AppCompatActivity act) {
        LiveDetailViewModel model = new ViewModelProvider(act).get(LiveDetailViewModel.class);
        model.getLiveDetail().observe(act, liveDetailResBean -> mIvHeart.setImageResource(liveDetailResBean.getData().isCollect() ? R.drawable.ic_heart_tint : R.drawable.ic_heart));
        commentModel.getCollect().observe(act, aBoolean -> {
            hideLoading();
            mIvHeart.setImageResource(aBoolean ? R.drawable.ic_heart_tint : R.drawable.ic_heart);
        });
    }

    /**
     * 评论点赞功能
     *
     * @param position 某一条评论index
     * @param act      AppCompatActivity
     * @param view     获取view强制转换TextView
     */
    private void addThumbs(int position, AppCompatActivity act, View view) {
        CommentBean.DataBean dataBean = mAdapter.getData().get(position);
        int commonId = dataBean.getCommon_id();
        commentModel.addRecording(CommentViewModel.CommentType.LIVE, String.valueOf(commonId));
        commentModel.getAddRecording().observe(act, aBoolean -> {
            TextView tvThumbs = (TextView) view;
            tvThumbs.setCompoundDrawablesWithIntrinsicBounds(aBoolean ? R.drawable.ic_thumb_tint : R.drawable.ic_thumb, 0, 0
                    , 0);
        });
    }

    /**
     * 查看自己评论或者所有人评论切换按钮
     *
     * @param id   评论id
     * @param view 切换按钮Switch
     */
    private void switchMineOrAll(String id, Switch view) {
        Switch switchView = view;
        mPageIndex = 1;
        isLookAll = !switchView.isChecked();
        commentModel.obtainCommentList(CommentViewModel.CommentType.LIVE, id, mPageIndex, isLookAll);
    }

    /**
     * huoqu互动信息列表
     *
     * @param id        评论id
     * @param pageIndex 页码 每次请求得页码数
     */
    private void obtainInteract(String id, int pageIndex) {
        showLoading("获取互动信息列表...");
        commentModel.obtainCommentList(CommentViewModel.CommentType.LIVE, id, pageIndex, isLookAll);
    }

    /**
     * 处理回调数据包含加载更多
     *
     * @param loadMoreModule BaseLoadMoreModule
     * @param dataBean       CommentBean
     */
    private void handleLoadData(BaseLoadMoreModule loadMoreModule, CommentBean dataBean) {
        if (!dataBean.isSuccess()) {
            ToastUtils.showShort(dataBean.getMsg());
            return;
        }
        List<CommentBean.DataBean> dataRes = dataBean.getData();
        if (ObjectUtils.isEmpty(dataRes)) {
            loadMoreModule.loadMoreEnd();
            return;
        }
        if (mPageIndex == 1) {
            mAdapter.setNewInstance(dataRes);
        } else {
            mAdapter.addData(dataRes);
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

    private void initRecyclerView() {
        RecyclerView rv = mFragmentProvider.getMineView().findViewById(R.id.rv_fragment_live_inner_interact);
        LinearLayoutManager manager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(manager);
        mAdapter = new LiveInteractInnerAdapter(R.layout.item_interact, null);
        rv.setAdapter(mAdapter);
    }


    private void initRefresh() {
        String id = CacheMemoryUtils.getInstance().get(Contact.ID);
        refreshLayout = mFragmentProvider.getMineView().findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mFragmentProvider.getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                mPageIndex = 1;
                obtainInteract(id, mPageIndex);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.tv_fragment_live_inner_interact_input:
                writeLeaveMsg();
                break;
            case R.id.iv_fragment_live_inner_interact_favorite:
                startFavorite();
                break;
            case R.id.iv_fragment_live_inner_interact_service:
                reword();
                break;
            default:
                break;
        }
    }

    /**
     * 打赏
     */
    private void reword() {
        Context context = mIvHeart.getContext();
        new XPopup.Builder(context)
                .hasShadowBg(true)
                .asCustom(new RewardCustomView(context))
                .show();
    }

    /**
     * 写评论
     */
    private void writeLeaveMsg() {
        mPageIndex = 1;
        new XPopup.Builder(mIvHeart.getContext())
                .hasShadowBg(false)
                .asCustom(new WriteLeaveMessageCustomView(mIvHeart.getContext(), CommentViewModel.CommentType.LIVE,
                        CacheMemoryUtils.getInstance().get(Contact.ID) + "", mPageIndex))
                .show();
    }

    /**
     * 收藏或者取消收藏
     */
    private void startFavorite() {
        showLoading("加载...");
        commentModel.setCollectStatus(CommentViewModel.CommentType.LIVE,
                CacheMemoryUtils.getInstance().get(Contact.ID) + "");
    }
}
