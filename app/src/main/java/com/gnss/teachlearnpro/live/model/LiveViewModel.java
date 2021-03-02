package com.gnss.teachlearnpro.live.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LivePlanBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class LiveViewModel extends BaseViewModel {
    private MutableLiveData<LivePlanBean> mMutablePlan = new MutableLiveData<>();
    private MutableLiveData<LivePlanBean> mMutableBack = new MutableLiveData<>();

    /**
     * 获取直播计划
     * 或者直播回放
     */
    public void obtainLivePlanOrBack(LiveType type, int pageIndex) {
        EasyHttp.post("Home/live")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("page", String.valueOf(pageIndex))
                .params("type", getParamWithType(type))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        if (type == LiveType.PLAN) {
                            mMutablePlan.postValue(null);
                        } else {
                            mMutableBack.postValue(null);
                        }
                        tipError(e, getTipMsg(type));
                    }

                    @Override
                    public void onSuccess(String s) {
                        LivePlanBean livePlanBean = GsonUtils.fromJson(s, LivePlanBean.class);
                        if (type == LiveType.PLAN) {
                            mMutablePlan.postValue(livePlanBean);
                        } else {
                            mMutableBack.postValue(livePlanBean);
                        }
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

    public LiveData<LivePlanBean> getLiveBack() {
        return mMutableBack;
    }
}
