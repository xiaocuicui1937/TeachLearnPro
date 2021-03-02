package com.gnss.teachlearnpro.main.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.HomePageBean;
import com.gnss.teachlearnpro.common.bean.InfoListResBean;
import com.gnss.teachlearnpro.common.bean.RecentStudyBean;
import com.gnss.teachlearnpro.common.bean.StudentWitnessResBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class HomePageViewModel extends BaseViewModel {
    private MutableLiveData<HomePageBean> mMutableHome = new MutableLiveData<>();
    private MutableLiveData<InfoListResBean> mMutableInfoList = new MutableLiveData<>();
    private MutableLiveData<StudentWitnessResBean> mMutableStudentWitnessList = new MutableLiveData<>();
    private MutableLiveData<RecentStudyBean> mMutableRecentStudyList = new MutableLiveData<>();

    /**
     * 获取首页信息
     */
    public void obtainHomePageInfo() {
        EasyHttp.post("Home/home")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableHome.postValue(null);
                        tipError(e, "访问获取首页接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        HomePageBean homePageBean = GsonUtils.fromJson(s, HomePageBean.class);
                        mMutableHome.postValue(homePageBean);
                        MeLog.e(s);
                    }
                });
    }

    public LiveData<HomePageBean> getHomeLiveData() {
        return mMutableHome;
    }

    /**
     * 获取话题文章列表
     */
    public void obtainInfoList(int pageIndex) {
        EasyHttp.post("Info/getList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("page", String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableHome.postValue(null);
                        tipError(e, "访问话题列表接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        InfoListResBean info = GsonUtils.fromJson(s, InfoListResBean.class);
                        mMutableInfoList.postValue(info);
                        MeLog.e(s);
                    }
                });
    }

    public LiveData<InfoListResBean> getInfoList() {
        return mMutableInfoList;
    }

    /**
     * 获取学生见证列表
     */
    public void obtainStudentWitnessList(int pageIndex) {
        EasyHttp.post("Student/getList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("page", String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableHome.postValue(null);
                        tipError(e, "访问学生见证列表接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        StudentWitnessResBean info = GsonUtils.fromJson(s, StudentWitnessResBean.class);
                        mMutableStudentWitnessList.postValue(info);
                        MeLog.e(s);
                    }
                });
    }

    public LiveData<StudentWitnessResBean> getStudentWitnessList() {
        return mMutableStudentWitnessList;
    }

    /**
     * 获取最近学习列表
     */
    public void obtainRecentStudyList(int pageIndex) {
        EasyHttp.post("Home/getAllList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("page", String.valueOf(pageIndex))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableRecentStudyList.postValue(null);
                        tipError(e, "访问最近学习列表接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        RecentStudyBean courseBean = GsonUtils.fromJson(s, RecentStudyBean.class);
                        mMutableRecentStudyList.postValue(courseBean);
                        MeLog.e(s);
                    }
                });
    }


    public LiveData<RecentStudyBean> getRecentStudyList() {
        return mMutableRecentStudyList;
    }
}
