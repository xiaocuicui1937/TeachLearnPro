package com.gnss.teachlearnpro.main.detail.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.common.bean.HomeDetailBean;
import com.gnss.teachlearnpro.common.bean.InfoDetailBean;
import com.gnss.teachlearnpro.common.bean.StudentWitnessDetailResBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class HomeListViewModel extends BaseViewModel {
    private MutableLiveData<HomeDetailBean> mMutableList = new MutableLiveData<>();

    public void obtainDetail(HomeListType type, String id) {

        EasyHttp.post(getUrl(type))
                .params("id", id)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipWithError(type, e);
                    }

                    @Override
                    public void onSuccess(String s) {

                        parseSuccess(type, s);
                    }
                });
    }

    private void parseSuccess(HomeListType type, String res) {
        if (type == HomeListType.INFO) {
            InfoDetailBean infoDetail = GsonUtils.fromJson(res, InfoDetailBean.class);
            if (infoDetail.isSuccess()) {
                InfoDetailBean.DataBean data = infoDetail.getData();
                HomeDetailBean home = new HomeDetailBean(data.getId(),data.getTitle(), data.getHead_img(), "",
                        data.getContent(), String.valueOf(data.getRead_number()),String.valueOf(data.getCollect_number()),data.getAuthor());
                mMutableList.postValue(home);
            } else {
                ToastUtils.showShort(infoDetail.getMsg());
            }
        } else if (type == HomeListType.STUDENT_WITNESS) {
            StudentWitnessDetailResBean studentRes = GsonUtils.fromJson(res, StudentWitnessDetailResBean.class);
            if (studentRes.isSuccess()) {
                StudentWitnessDetailResBean.DataBean data = studentRes.getData();
                HomeDetailBean home = new HomeDetailBean(data.getId(),data.getTitle(), data.getImg(), "",data.getDetails());
                mMutableList.postValue(home);
            } else {
                ToastUtils.showShort(studentRes.getMsg());
            }
        }
    }

    private void tipWithError(HomeListType type, ApiException e) {
        mMutableList.postValue(null);
        String tip = "";
        if (type == HomeListType.INFO) {
            tip = "访问文章详情接口失败";
        } else if (type == HomeListType.STUDENT_WITNESS) {
            tip = "访问学员见证详情接口失败";
        }
        tipError(e, tip);
    }

    private String getUrl(HomeListType type) {
        if (HomeListType.INFO == type) {
            return "Info/getDetail";
        }
        if (HomeListType.STUDENT_WITNESS == type) {
            return "Student/getStudentDetail";
        }
        return "";
    }

    public enum HomeListType {
        INFO,
        STUDENT_WITNESS
    }

    public LiveData<HomeDetailBean> getList() {
        return mMutableList;
    }
}
