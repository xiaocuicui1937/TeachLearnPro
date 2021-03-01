package com.gnss.teachlearnpro.common.ui;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.ActivityUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

public class WriteLeaveMessageCustomView extends BottomPopupView {
    private CommentViewModel.CommentType mCommentType;
    private String mId;
    private ZLoadingDialog zLoadingDialog;
    private EditText mEtWriteComment;
    private int mPageIndex;

    public WriteLeaveMessageCustomView(@NonNull Context context, CommentViewModel.CommentType commentType, String id, int pageIndex) {
        super(context);
        this.mCommentType = commentType;
        this.mId = id;
        this.mPageIndex = pageIndex;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_bottom_write_comment;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_layout_bottom_write_comment_close).setOnClickListener(view -> dialog.dismiss());
        findViewById(R.id.tv_layout_bottom_write_comment_send).setOnClickListener(view -> sendComment());
        mEtWriteComment = findViewById(R.id.et_layout_write_comment);
    }

    /**
     * 发表评论
     */
    private void sendComment() {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        CommentViewModel model = new ViewModelProvider(act).get(CommentViewModel.class);
        showLoading("留言中");

        model.writeComment(mCommentType, mId, mEtWriteComment.getText().toString());
        model.obtainCommentList(mCommentType, mId, mPageIndex, true);

        model.getWriteComment().observe(act, aBoolean -> {
            hideLoading();
            if (aBoolean) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * 0.4);
    }


    /**
     * 显示加载动画
     *
     * @param msg 提示信息
     */
    public void showLoading(String msg) {
        if (zLoadingDialog == null) {
            zLoadingDialog = new ZLoadingDialog(ActivityUtils.getTopActivity());
        }

        zLoadingDialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLUE)//颜色
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setHintText(msg)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.BLUE)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
//                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .show();
    }

    /**
     * 关闭加载动画
     */
    public void hideLoading() {
        if (zLoadingDialog != null) {
            zLoadingDialog.dismiss();
            zLoadingDialog = null;
        }
    }

}
