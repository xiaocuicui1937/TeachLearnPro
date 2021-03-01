package com.gnss.teachlearnpro.live.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.gnss.teachlearnpro.common.bean.LivePlanBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class LiveViewModel extends BaseViewModel {
    private MutableLiveData<LivePlanBean> mMutablePlan = new MutableLiveData<>();

    /**
     * 获取直播计划
     * 或者直播回放
     */
    public void obtainLivePlanOrBack(LiveType type,int pageIndex) {
        EasyHttp.post("Home/live")
                .params("page",String.valueOf(pageIndex))
                .params("type", getParamWithType(type))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutablePlan.postValue(null);
                        tipError(e, getTipMsg(type));
                    }

                    @Override
                    public void onSuccess(String s) {
                        LivePlanBean livePlanBean = GsonUtils.fromJson(s, LivePlanBean.class);
                        mMutablePlan.postValue(livePlanBean);
                    }
                });
    }

    public enum LiveType {
        PLAN, BACK
    }

    private String getParamWithType(LiveType type) {
        if (type == LiveType.PLAN) {
            return "1";
        }
        if (type == LiveType.BACK) {
            return "2";
        }
        return "-1";
    }

    private String getTipMsg(LiveType type) {
        if (type == LiveType.PLAN) {
            return "访问直播计划接口失败";
        } else if (type == LiveType.BACK) {
            return "访问直播回放接口失败";
        }
        return "";
    }

    public LiveData<LivePlanBean> getLive() {
        return mMutablePlan;
    }
}
