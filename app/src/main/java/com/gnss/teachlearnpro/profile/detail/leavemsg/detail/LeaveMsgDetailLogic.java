package com.gnss.teachlearnpro.profile.detail.leavemsg.detail;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LeaveMsgBean;
import com.gnss.teachlearnpro.common.logic.BaseLogic;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;

public class LeaveMsgDetailLogic extends BaseLogic implements View.OnClickListener {
    private ActivityProvider mProvider;
    private TextView mTvTitle;
    private TextView mTvDate;
    private EditText mEtContent;
    private boolean isEdit = false;
    private LeaveMsgBean.DataBean mLeaveMsg;
    private CommentViewModel model;

    public LeaveMsgDetailLogic(ActivityProvider provider) {
        this.mProvider = provider;
        setTitle("");
        initView();
        showContent();
        addRes();
    }

    private void addRes() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        model = new ViewModelProvider(act).get(CommentViewModel.class);
        model.getWriteComment().observe(act, aBoolean -> {
            handleRes(aBoolean);
        });
        model.getDeleteComment().observe(act, aBoolean -> handleRes(aBoolean));
        model.getUpdateComment().observe(act, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                handleRes(aBoolean);
            }
        });
    }

    private void handleRes(Boolean aBoolean) {
        hideLoading();
        if (aBoolean) {
            ActivityUtils.finishActivity(LeaveMsgDetailActivity.class);
        }
    }

    private void showContent() {
        mLeaveMsg = CacheMemoryUtils.getInstance().get(Contact.LEAVE_MSG_DETAIL);
        mTvTitle.setText(mLeaveMsg.getTitle());
        mTvDate.setText(TimeUtils.millis2String(mLeaveMsg.getCreate_time() * 1000L));
        mEtContent.setText(mLeaveMsg.getContent());
        KeyboardUtils.hideSoftInput(mEtContent);
    }


    private void initView() {
        mTvTitle = mProvider.findViewById(R.id.tv_activity_leave_msg_detail_title);
        mTvDate = mProvider.findViewById(R.id.tv_activity_leave_msg_detail_date);
        mEtContent = mProvider.findViewById(R.id.et_activity_leave_msg_detail_content);
        mEtContent.setEnabled(isEdit);

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
        String content = mEtContent.getText().toString();
        if (ObjectUtils.isEmpty(content)) {
            ToastUtils.showShort("留言不能为空!");
            return;
        }

        showLoading("保存留言");
        model.updateComment(String.valueOf(mLeaveMsg.getComment_id()), content);
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
       model.deleteComment(String.valueOf(mLeaveMsg.getComment_id()));
    }


}
