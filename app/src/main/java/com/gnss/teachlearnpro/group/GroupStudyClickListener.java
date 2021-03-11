package com.gnss.teachlearnpro.group;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ecommerce.common.dataconvert.MultipleItemEntity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.group.detail.GroupStudyDetailActivity;
import com.gnss.teachlearnpro.group.qr.QRCenterView;
import com.lxj.xpopup.XPopup;

public class GroupStudyClickListener implements OnItemClickListener {

    public GroupStudyClickListener() {

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        MultipleItemEntity entity = (MultipleItemEntity) adapter.getItem(position);
        ConstraintLayout clRoot = (ConstraintLayout) adapter.getViewByPosition(position, R.id.cl_item_group_study);
        createQRCodeorToDetail(view, entity, clRoot);

    }

    private void createQRCodeorToDetail(View view, MultipleItemEntity entity, ConstraintLayout clRoot) {

        boolean isBlur = entity.getField(Contact.BLUR);
        String wechatImgUrl = entity.getField(Contact.LOGO_URL);
        int pwd = entity.getField(Contact.PWD);
        if (isBlur) {
            new XPopup.Builder(view.getContext())
                    .asBottomList("", new String[]{"已有密码", "没有密码"}, (position, text) -> {
                        if (position == 0) {
                            createInputSecretMatchDialog(view, String.valueOf(pwd), clRoot, entity);
                        } else {
                            createQrDialog(view, wechatImgUrl);
                        }
                    }).show();
        } else {
            CacheMemoryUtils.getInstance().put(Contact.ID, entity.getField(Contact.ID));
            ActivityUtils.startActivity(GroupStudyDetailActivity.class);
        }
    }

    private void createInputSecretMatchDialog(View view, String originPwd, ConstraintLayout clRoot, MultipleItemEntity entity) {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        GroupStudyViewModel model = new ViewModelProvider(act).get(GroupStudyViewModel.class);
        new XPopup.Builder(view.getContext())
                .dismissOnTouchOutside(false)
                .asInputConfirm("课程密码", "", "请输入课程密码", text -> {
                    model.matchPasswordWithGroup(entity.getField(Contact.ID), text);
//                    if (ObjectUtils.equals(originPwd, text)) {
//
//                    } else {
//                        ToastUtils.showShort("密码不正确,请重新输入!");
//                    }
                }).show();
        model.getGroupMatchPwdOrNot().observe(act, aBoolean -> updateItemBlur(clRoot, entity, !aBoolean));
    }

    private void updateItemBlur(ConstraintLayout clRoot, MultipleItemEntity entity, boolean isBlur) {
        TextView titleTv = clRoot.findViewById(R.id.tv_item_group_study_title);
        TextView dateTv = clRoot.findViewById(R.id.tv_item_group_study_date);
        TextView timeTv = clRoot.findViewById(R.id.tv_item_group_study_time);
        TextView locationTv = clRoot.findViewById(R.id.tv_item_group_study_location);
        TextView groupTv = clRoot.findViewById(R.id.tv_item_group_study_group);

        String title = entity.getField(Contact.TITLE);
        String date = entity.getField(Contact.PlAN_DATE);
        String location = entity.getField(Contact.PlAN_LOCATION);
        String group = entity.getField(Contact.PlAN_GROUP);
        String time = entity.getField(Contact.PlAN_TIME);


        ImageView iv = clRoot.findViewById(R.id.iv_item_group_study_lock);
        TextView tv = clRoot.findViewById(R.id.tv_item_group_study_blur);
        if (!isBlur) {
            entity.setValue(Contact.BLUR, false);
        }
        setBlur(titleTv, dateTv, timeTv, locationTv, groupTv, title, date, location, group, time, iv, tv, isBlur);
    }

    private void setBlur(TextView titleTv, TextView dateTv, TextView timeTv, TextView locationTv, TextView groupTv, String title, String date, String location, String group, String time, ImageView iv, TextView tv, boolean isBlur) {
        if (isBlur) {
            titleTv.setText("xxxxx");
            dateTv.setText("xxxxx");
            timeTv.setText("xxxxx");
            locationTv.setText("xxxxx");
            groupTv.setText("xxxxx");
        } else {
            titleTv.setText(title);
            dateTv.setText(date);
            timeTv.setText(time);
            locationTv.setText(location);
            groupTv.setText(group);
        }
        iv.setVisibility(isBlur ? View.VISIBLE : View.GONE);
        tv.setVisibility(isBlur ? View.VISIBLE : View.GONE);
    }

    private void createQrDialog(View view, String wechatImgUrl) {
        ConstraintLayout rootView = (ConstraintLayout) LayoutInflater.from(view.getContext()).inflate(R.layout.layout_qrcode, null);
        new XPopup.Builder(view.getContext())
                .dismissOnTouchOutside(false)
                .asCustom(new QRCenterView(rootView.getContext(), wechatImgUrl))
                .show();
    }


}
