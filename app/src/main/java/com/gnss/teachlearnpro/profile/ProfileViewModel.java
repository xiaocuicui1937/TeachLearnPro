package com.gnss.teachlearnpro.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.AvatarResBean;
import com.gnss.teachlearnpro.common.bean.BaseResBean;
import com.gnss.teachlearnpro.common.bean.ProfileBean;
import com.gnss.teachlearnpro.common.model.BaseViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends BaseViewModel {
    private MutableLiveData<ProfileBean> mMutableProfile = new MutableLiveData<>();
    private MutableLiveData<BaseResBean> mMutableLogout = new MutableLiveData<>();
    private MutableLiveData<BaseResBean> mMutableUpdateInfo = new MutableLiveData<>();
    private MutableLiveData<AvatarResBean> mMutableUpdateAvator = new MutableLiveData<>();

    public void obtainProfile() {
        EasyHttp.post("Personal/getPersonal")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableProfile.postValue(null);
                        tipError(e, "访问获取个人信息失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        ProfileBean profileBean = GsonUtils.fromJson(s, ProfileBean.class);
                        mMutableProfile.postValue(profileBean);
                    }
                });

    }

    public LiveData<ProfileBean> getProfile() {
        return mMutableProfile;
    }


    public void logout() {
        EasyHttp.post("User/loginOut")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableProfile.postValue(null);
                        tipError(e, "访问获取个人信息失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        BaseResBean baseResBean = GsonUtils.fromJson(s, BaseResBean.class);
                        mMutableLogout.postValue(baseResBean);
                    }
                });
    }

    public LiveData<BaseResBean> getLogout() {
        return mMutableLogout;
    }


    public void updatePerson(String... persons) {
        String token = SPUtils.getInstance().getString(Contact.TOEKN);
        EasyHttp.post("Personal/updatePersonal")
                .headers(Contact.HEADER_TOKEN, token)
                .params("nickname", persons[0])
                .params("avatar", persons[1])
                .params("province_id", persons[2])
                .params("city_id", persons[3])
                .params("area_id", persons[4])
                .params("sex", persons[5])
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableUpdateInfo.postValue(null);
                        tipError(e, "访问更新个人信息失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        BaseResBean baseResBean = GsonUtils.fromJson(s, BaseResBean.class);
                        mMutableUpdateInfo.postValue(baseResBean);
                        ToastUtils.showShort(baseResBean.getMsg());
                    }
                });
    }

    public LiveData<BaseResBean> getUpdateInfo() {
        return mMutableUpdateInfo;
    }


    public void updateAvator(String path) {
        File file = FileUtils.getFileByPath(path);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        EasyHttp.post("Upload/uploadFile ")
                .headers(Contact.HEADER_TOKEN, SPUtils.getInstance().getString(Contact.TOEKN))
                .requestBody(requestBody)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableUpdateAvator.postValue(null);
                        tipError(e, "访问更新头像接口失败");
                    }

                    @Override
                    public void onSuccess(String s) {

                        AvatarResBean avatarResBean = GsonUtils.fromJson(s, AvatarResBean.class);
                        ToastUtils.showShort(avatarResBean.getMsg());
                        mMutableUpdateAvator.postValue(avatarResBean);
                    }
                });
    }

    public LiveData<AvatarResBean> getUpdateAvator() {
        return mMutableUpdateAvator;
    }

    public void obtainLeaveMsg() {
        String token = SPUtils.getInstance().getString(Contact.TOEKN);
        EasyHttp.post("Personal/getMyReview")
                .headers(Contact.HEADER_TOKEN, token)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        tipError(e, "访问获取留言列表失败");
                    }

                    @Override
                    public void onSuccess(String s) {
                        MeLog.e("留言:"+s);
                    }
                });
    }

}
