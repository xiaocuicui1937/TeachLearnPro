package com.gnss.teachlearnpro.profile.detail.leavemsg;

import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheMemoryUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LeaveMsgBean;
import com.gnss.teachlearnpro.common.viewmodel.CommentViewModel;
import com.gnss.teachlearnpro.profile.detail.leavemsg.detail.LeaveMsgDetailActivity;

public class LeaveMsgItemCLickListener implements OnItemClickListener {


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        LeaveMsgBean.DataBean dataBean = (LeaveMsgBean.DataBean) adapter.getData().get(position);
        CacheMemoryUtils instance = CacheMemoryUtils.getInstance();
        instance.put(Contact.LEAVE_MSG_DETAIL, dataBean);
        instance.put(Contact.LEAVE_MSG_TYPE, CommentViewModel.CommentType.LIVE);
        ActivityUtils.startActivity(LeaveMsgDetailActivity.class);
    }
}
