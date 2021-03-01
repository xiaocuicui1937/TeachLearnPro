package com.gnss.teachlearnpro.profile.detail.record;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.gnss.teachlearnpro.common.bean.RecordResBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

public class RecordViewModel extends BaseViewModel {
    public enum RecordType {
        LIVE, COURSE
    }

    private MutableLiveData<List<RecordResBean.DataBean>> mMutableLiveRecords = new MutableLiveData<>();
    private MutableLiveData<List<RecordResBean.DataBean>> mMutableCourseRecords = new MutableLiveData<>();

    public void obtainRecords(RecordType type) {
        String url;
        String tip;
        if (type == RecordType.COURSE) {
            url = "Personal/getCourse";
            tip = "访问获取接课程记录口失败";
        } else {
            url = "Personal/getLive";
            tip = "访问获取直播记录失败";
        }
        MeLog.e("person:" + url);
        EasyHttp.post(url)
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, tip);
                        updateData(type,null);
                    }

                    @Override
                    public void onSuccess(String s) {
                        RecordResBean res = GsonUtils.fromJson(s, RecordResBean.class);
                        if (res.isSuccess()) {
                            updateData(type,res.getData());
                        } else {
                            ToastUtils.showShort(res.getMsg());
                            updateData(type,null);
                        }
                    }
                });
    }


    public void updateData(RecordType type, List<RecordResBean.DataBean> datas) {
        if (type == RecordType.COURSE) {
            mMutableCourseRecords.postValue(datas);
        } else if (type == RecordType.LIVE) {
            mMutableLiveRecords.postValue(datas);
        }
    }

    public LiveData<List<RecordResBean.DataBean>> getLiveRecords() {
        return mMutableLiveRecords;
    }

    public LiveData<List<RecordResBean.DataBean>> getCourseRecords() {
        return mMutableCourseRecords;
    }
}
