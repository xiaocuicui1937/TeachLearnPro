package com.gnss.teachlearnpro.live.liveplan.detail.adapter;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ItemType;
import com.gnss.teachlearnpro.course.detail.list.HtmlLoadManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LiveInnerDetailAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, BaseViewHolder> {
    private HtmlLoadManager mHtmlLoadManager;
    public LiveInnerDetailAdapter(@Nullable List<MultipleItemEntity> data) {
        super(data);
        mHtmlLoadManager = new HtmlLoadManager();

        addItemType(ItemType.SIGNlE_TEXT_TYPE, R.layout.item_live_inner_text);
        addItemType(ItemType.IMAGE_TYPE, R.layout.item_live_inner_img);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        int viewType = baseViewHolder.getItemViewType();
        switch (viewType) {
            case ItemType.SIGNlE_TEXT_TYPE:
                createText(baseViewHolder, multipleItemEntity);
                break;
            case ItemType.IMAGE_TYPE:
                createImage(baseViewHolder, multipleItemEntity);
                break;
            default:
                break;
        }
    }
    private void createImage(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        TextView titleTv =baseViewHolder.getView(R.id.tv_item_live_inner_img_title);
        ImageView iv =baseViewHolder.getView(R.id.iv_item_live_inner_img);
        String url = multipleItemEntity.getField(Contact.PlAN_URL);
        String title = multipleItemEntity.getField(Contact.TITLE);
        titleTv.setText(title);
        Glide.with(iv.getContext()).load(url).into(iv);
//        iv.setImageResource(R.drawable.test_inner_img);
    }

    private void createText(BaseViewHolder baseViewHolder, MultipleItemEntity multipleItemEntity) {
        TextView titleTv =baseViewHolder.getView(R.id.tv_item_live_inner_title);
        FrameLayout flContent =baseViewHolder.getView(R.id.fl_fragment_group_detail_content);
        String title = multipleItemEntity.getField(Contact.TITLE);
        String content = multipleItemEntity.getField(Contact.CONTENT_TITLE);
        titleTv.setText(title);
        mHtmlLoadManager.initWebView(flContent);
        mHtmlLoadManager.loadHtmlCode(content);
    }

    /**
     * 销毁webview防止内存泄漏
     */
    public void destroyWebView() {
        mHtmlLoadManager.destroyWebView();
    }
}
