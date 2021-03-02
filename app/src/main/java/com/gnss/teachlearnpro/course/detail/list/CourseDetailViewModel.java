package com.gnss.teachlearnpro.course.detail.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CourseDetailBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

public class CourseDetailViewModel extends BaseViewModel {
    private MutableLiveData<CourseDetailBean> mUtableCourse = new MutableLiveData<>();

    public void obtainCourseDetail(String id) {
        EasyHttp.post("Course/getCourseDetail")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("id", id)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问课程详情接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        CourseDetailBean course = GsonUtils.fromJson(s,CourseDetailBean.class);
                        mUtableCourse.postValue(course);

                    }
                });
    }


    public LiveData<CourseDetailBean> getCourse() {
        return mUtableCourse;
    }

}
