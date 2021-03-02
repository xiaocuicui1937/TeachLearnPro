package com.gnss.teachlearnpro.group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.BaseResBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;

public class GroupStudyViewModel extends BaseViewModel {
    private MutableLiveData<String> mMutableGroupStudy = new MutableLiveData<>();
    private MutableLiveData<Boolean> mMutableGroupEntry = new MutableLiveData<>();
    private MutableLiveData<Boolean> mMutableGroupEntryOrNot = new MutableLiveData<>();

    public void obtainGroupStudyList(int pageIndex) {
        EasyHttp.post("Team/getList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("page",String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableGroupStudy.postValue(null);
                        tipError(e, "访问获取课程列表失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        mMutableGroupStudy.postValue(s);
                    }
                });
    }

    public LiveData<String> getGroupStudy() {
        return mMutableGroupStudy;
    }


    public void submitGroupStudyEntry(String title, String subTitle, String ids) {
        HashMap<String, String> params = new HashMap<>();
        params.put("title", title);
        if (ObjectUtils.isNotEmpty(subTitle)) {
            params.put("title_second", subTitle);
        }

        //最后一个字符删除
        ids.substring(0, ids.length() - 1);
        params.put("team_ids", ids);

        EasyHttp.post("Team/addTeamStudent")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params(params)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableGroupStudy.postValue(null);
                        tipError(e, "访问保存报名信息接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        MeLog.e(s);
                        BaseResBean res = GsonUtils.fromJson(s, BaseResBean.class);
                        if (res.isSuccess()) {
                            mMutableGroupEntry.postValue(true);
                        } else {
                            ToastUtils.showShort(res.getMsg());
                        }
                    }
                });
    }

    public LiveData<Boolean> getGroupStudyEntry() {
        return mMutableGroupEntry;
    }

    /**
     * 是否已经报名
     */
    public void isGroupEntry() {
        EasyHttp.post("Team/isTeamStudent")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableGroupEntryOrNot.postValue(false);
                        tipError(e, "访问获取是否已经报名失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        MeLog.e("是否已经报名：" + s);
                        BaseResBean res = GsonUtils.fromJson(s, BaseResBean.class);
                        mMutableGroupEntryOrNot.postValue(res.isSuccess());

                    }
                });

    }


    public LiveData<Boolean> getGroupStudyEntryOrNot() {
        return mMutableGroupEntryOrNot;
    }


}
