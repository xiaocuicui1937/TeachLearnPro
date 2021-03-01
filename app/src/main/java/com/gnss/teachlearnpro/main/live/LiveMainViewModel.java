package com.gnss.teachlearnpro.main.live;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.gnss.teachlearnpro.common.bean.LiveListBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class LiveMainViewModel extends BaseViewModel {
    private MutableLiveData<LiveListBean> mUtableLiveList = new MutableLiveData<>();

    public void obtainLiveList(int pageIndex) {
        EasyHttp.post("Home/live")
                .params("page",String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问直播列表接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        LiveListBean res = GsonUtils.fromJson(s, LiveListBean.class);
                        mUtableLiveList.postValue(res);
                    }
                });
    }

    public LiveData<LiveListBean> getLiveList() {
        return mUtableLiveList;
    }
}
