package com.gnss.teachlearnpro.profile.detail.leavemsg.detail;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;

public class LeaveMsgDetailLogic extends BaseLogic implements View.OnClickListener {
    private ActivityProvider mProvider;
    private TextView mTvTitle;
    private TextView mTvDate;
    private EditText mEtContent;
    private boolean isEdit = false;

    public LeaveMsgDetailLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle("");
        initView();
    }

    private void initView() {
        mTvTitle = mProvider.findViewById(R.id.tv_activity_leave_msg_detail_title);
        mTvDate = mProvider.findViewById(R.id.tv_activity_leave_msg_detail_date);
        mEtContent = mProvider.findViewById(R.id.et_activity_leave_msg_detail_content);

        mProvider.findViewById(R.id.iv_activity_leave_msg_delete).setOnClickListener(this);
        mProvider.findViewById(R.id.iv_activity_leave_msg_edit).setOnClickListener(this);
        mProvider.findViewById(R.id.iv_activity_leave_msg_sure).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int viewType = view.getId();
        switch (viewType) {
            case R.id.iv_activity_leave_msg_delete:
                deleteContent();
                break;
            case R.id.iv_activity_leave_msg_edit:
                editContent();
                break;
            case R.id.iv_activity_leave_msg_sure:
                submitContent();
                break;
            default:
                break;
        }
    }

    /**
     * 提交编辑的内容
     */
    private void submitContent() {

    }

    /**
     * 点击开启编辑内容开关
     */
    private void editContent() {
        isEdit = !isEdit;
        mEtContent.setEnabled(isEdit);
        ToastUtils.showShort(isEdit ? "开启编辑" : "关闭编辑");
    }

    /**
     * 删除所有的内容
     */
    private void deleteContent() {
        mEtContent.getText().clear();
    }


}
