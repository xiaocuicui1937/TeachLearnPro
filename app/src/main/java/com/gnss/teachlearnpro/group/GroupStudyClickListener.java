package com.gnss.teachlearnpro.group;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.group.detail.GroupStudyDetailActivity;
import com.gnss.teachlearnpro.group.qr.QRCenterView;
import com.gnss.teachlearnpro.main.MainActivity;
import com.lxj.xpopup.XPopup;

public class GroupStudyClickListener implements OnItemClickListener {
    public GroupStudyClickListener() {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        MultipleItemEntity entity = (MultipleItemEntity) adapter.getItem(position);
        createQRCodeorToDetail(view, entity);

    }

    private void createQRCodeorToDetail(View view, MultipleItemEntity entity) {
        boolean isBlur = entity.getField(Contact.BLUR);
        String wechatImgUrl = entity.getField(Contact.LOGO_URL);
        if (isBlur) {
            ConstraintLayout rootView = (ConstraintLayout) LayoutInflater.from(view.getContext()).inflate(R.layout.layout_qrcode, null);
            new XPopup.Builder(view.getContext())
                    .dismissOnTouchOutside(false)
                    .asCustom(new QRCenterView(rootView.getContext(),wechatImgUrl))
                    .show();
        } else {
            CacheMemoryUtils.getInstance().put(Contact.ID, entity.getField(Contact.ID));
            ActivityUtils.startActivity(GroupStudyDetailActivity.class);
        }
    }


}
