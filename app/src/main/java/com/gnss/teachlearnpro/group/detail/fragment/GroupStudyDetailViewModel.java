package com.gnss.teachlearnpro.group.detail.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CommentBean;
import com.gnss.teachlearnpro.common.bean.GroupStudyDetailBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class GroupStudyDetailViewModel extends BaseViewModel {
    private MutableLiveData<GroupStudyDetailBean.DataBean> mUtableGroupDetail = new MutableLiveData<>();

    public void obtainGroupStudyDetail(String id) {
        EasyHttp.post("Team/getTeamDetail")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("team_id", id)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问获取小组详情接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        GroupStudyDetailBean res = GsonUtils.fromJson(s, GroupStudyDetailBean.class);
                        if (!res.isSuccess()) {
                            ToastUtils.showShort(res.getMsg());
                        }
                        mUtableGroupDetail.postValue(res.getData());
                    }
                });
    }

    public LiveData<GroupStudyDetailBean.DataBean> getGroupDetail() {
        return mUtableGroupDetail;
    }


}
