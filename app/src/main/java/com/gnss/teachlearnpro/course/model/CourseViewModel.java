package com.gnss.teachlearnpro.course.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.CourseBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.jetbrains.annotations.NotNull;

public class CourseViewModel extends BaseViewModel {
    private MutableLiveData<CourseBean> mMutableCourse = new MutableLiveData<>();

    public void obtainCourseList(int pageIndex) {
        String classIdStr = getId();
        EasyHttp.post("Course/getList")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .params("page", String.valueOf(pageIndex))
                .params("class_id", classIdStr)
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

    @NotNull
    private String getId() {
        int classId = CacheMemoryUtils.getInstance().get(Contact.ID);
        String classIdStr = "";
        if (classId != 0) {
            classIdStr =String.valueOf(classId);
        }
        return classIdStr;
    }

    public LiveData<CourseBean> getCourseList() {
        return mMutableCourse;
    }


}
