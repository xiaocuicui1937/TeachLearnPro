package com.gnss.teachlearnpro.course.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.gnss.teachlearnpro.common.bean.CourseBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class CourseViewModel extends BaseViewModel {
    private MutableLiveData<CourseBean> mMutableCourse = new MutableLiveData<>();

    public void obtainCourseList() {
        EasyHttp.post("Course/getList")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问获取系列课程列表失败");
                        mMutableCourse.postValue(null);
                    }

                    @Override
                    public void onSuccess(String s) {
                        CourseBean courseBean = GsonUtils.fromJson(s, CourseBean.class);
                        mMutableCourse.postValue(courseBean);
                    }
                });
    }

    public LiveData<CourseBean> getCourseList() {
        return mMutableCourse;
    }


}
