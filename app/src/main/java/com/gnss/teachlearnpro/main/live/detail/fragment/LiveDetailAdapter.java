package com.gnss.teachlearnpro.main.live.detail.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.course.detail.list.HtmlLoadManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiveDetailAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, BaseViewHolder> implements LoadMoreModule {
    private HtmlLoadManager mHtmlLoadManager;

    public LiveDetailAdapter(@Nullable List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.HEAD_TYPE, R.layout.item_live_detail_head);
        addItemType(ItemType.COMMENT_TYPE, R.layout.item_live_detail_comment);
        mHtmlLoadManager = new HtmlLoadManager();
        addChildClickViewIds(R.id.switch_item_live_detail_head,R.id.tv_item_live_detail_thumbs);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItemEntity liveDetailBean) {
        int viewType = baseViewHolder.getItemViewType();
        switch (viewType) {
            case ItemType.HEAD_TYPE:
                createHead(baseViewHolder, liveDetailBean);
                break;
            case ItemType.COMMENT_TYPE:
                createComment(baseViewHolder, liveDetailBean);
                break;
            default:
                break;
        }
    }

    private void createComment(BaseViewHolder baseViewHolder, MultipleItemEntity item) {
        ImageView ivAvator = baseViewHolder.getView(R.id.iv_item_live_detail_comment_avator);
        TextView tvNick = baseViewHolder.getView(R.id.tv_item_live_detail_comment_nick);
        TextView tvDate = baseViewHolder.getView(R.id.tv_item_detail_comment_date);
        TextView tvContent = baseViewHolder.getView(R.id.tv_item_live_detail_content);
        TextView tvThumbs = baseViewHolder.getView(R.id.tv_item_live_detail_thumbs);
        TextView tvMessage = baseViewHolder.getView(R.id.tv_item_live_detail_message);

        TextView tvResponseContent = baseViewHolder.getView(R.id.tv_item_live_detail_answer);
        TextView tvResponseMan = baseViewHolder.getView(R.id.tv_item_live_detail_answer_man);
        ConstraintLayout clResponse = baseViewHolder.getView(R.id.cl_item_live_detail_answer);
        CommentBean.DataBean data = item.getField(Contact.CONTENT_SUB_TITLE);
        //加载头像
        Glide.with(ivAvator.getContext()).load(data.getImages()).placeholder(R.drawable.test_avator_circle).into(ivAvator);
        tvNick.setText(data.getNickname());
        tvDate.setText(data.getCreate_time());
        tvContent.setText(data.getContent());
        tvThumbs.setText(String.valueOf(data.getLike_num()));
        tvMessage.setText(String.valueOf(data.getFraction()));
        if (ObjectUtils.isNotEmpty(data.getRespond())) {
            clResponse.setVisibility(View.VISIBLE);
            tvResponseContent.setText(data.getRespond_content());
            tvResponseMan.setText(data.getRespond() + " 回复");
        } else {
            clResponse.setVisibility(View.GONE);
        }
    }


    /**
     * 处理head传递过来的数据并显示
     * 日程详情和查看自己以及查看所有
     * 默认查看所有
     */
    private void createHead(BaseViewHolder baseViewHolder, MultipleItemEntity item) {
        FrameLayout flContent = baseViewHolder.getView(R.id.fl_fragment_group_detail_content);
        mHtmlLoadManager.initWebView(flContent);
        mHtmlLoadManager.loadHtmlCode(item.getField(Contact.TITLE));
    }


    /**
     * 销毁webview防止内存泄漏
     */
    public void destroyWebView() {
        mHtmlLoadManager.destroyWebView();
    }
}
