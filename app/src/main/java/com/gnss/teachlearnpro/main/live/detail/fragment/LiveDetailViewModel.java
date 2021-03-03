package com.gnss.teachlearnpro.main.live.detail.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LiveDetailResBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class LiveDetailViewModel extends BaseViewModel {
    private MutableLiveData<LiveDetailResBean> mUtableLiveDetail = new MutableLiveData<>();

    public void obtainLiveDetail(String id) {
        String token = SPUtils.getInstance().getString(Contact.TOEKN);
        EasyHttp.post("Home/liveDetails")
                .headers(Contact.HEADER_TOKEN,token)
                .params("id", id)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问直播详情接口失败");
                        mUtableLiveDetail.postValue(null);
                    }

                    @Override
                    public void onSuccess(String s) {
                        LiveDetailResBean res = GsonUtils.fromJson(s, LiveDetailResBean.class);
                        mUtableLiveDetail.postValue(res);
                    }
                });
    }

    public LiveData<LiveDetailResBean> getLiveDetail() {
        return mUtableLiveDetail;
    }
}
