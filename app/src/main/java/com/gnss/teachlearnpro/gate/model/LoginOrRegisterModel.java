package com.gnss.teachlearnpro.gate.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.bean.LoginOrRegisterBean;
import com.gnss.teachlearnpro.common.bean.LoginOrRegisterParamBean;
import com.gnss.teachlearnpro.common.bean.VerityCodeBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.HashMap;

public class LoginOrRegisterModel extends ViewModel {
    private MutableLiveData<VerityCodeBean> mMutableVerityCode = new MutableLiveData<>();
    private MutableLiveData<LoginOrRegisterBean> mMutableLoginOrRegister = new MutableLiveData<>();

    public void obtainVerityCode(String phone, String mode) {
        if (ObjectUtils.isEmpty(phone)) {
            ToastUtils.showShort(R.string.phone_not_empty);
            mMutableVerityCode.setValue(null);
            return;
        }
        MeLog.d("model:" + mode);
        EasyHttp.post("Sms/sendSms")
                .params("phone", phone)
                .params("model", mode)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableVerityCode.setValue(null);
                        ToastUtils.showShort("访问获取验证码接口:" + e.getMessage() + "\n错误码:" + e.getCode());
                    }

                    @Override
                    public void onSuccess(String s) {
                        VerityCodeBean verityCodeBean = GsonUtils.fromJson(s, VerityCodeBean.class);
                        mMutableVerityCode.postValue(verityCodeBean);
                    }
                });
    }

    public LiveData<VerityCodeBean> getVerityCodeLiveData() {
        return mMutableVerityCode;
    }

    public void submitLoginOrRegister(boolean isRegister, LoginOrRegisterParamBean param) {
        if (!checkParamWithLoginOrRegister(isRegister, param)) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", param.phone);
        params.put("password", param.password);
        if (Contact.LOGIN_WITH_PHONE == param.type) {


        } else if (Contact.LOGIN_WITH_SMS == param.type) {
            params.put("code", String.valueOf(param.code));
        }
        params.put("type", String.valueOf(param.type));
        if (isRegister) {
            params.put("code", String.valueOf(param.code));
            params.put("nickname", param.nickname);
        }


        String url = isRegister ? "User/phoneReg" : "User/pcLogin";
        MeLog.d("submitLoginOrRegister:" + param.toString() + "\n" + url);
        EasyHttp.post(url)
                .params(params)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        mMutableLoginOrRegister.postValue(null);
                        ToastUtils.showShort("访问登录接口:" + e.getMessage() + "\n错误码:" + e.getCode());
                    }

                    @Override
                    public void onSuccess(String s) {
                        MeLog.d(s);
                        LoginOrRegisterBean verityCodeBean = GsonUtils.fromJson(s, LoginOrRegisterBean.class);
                        mMutableLoginOrRegister.postValue(verityCodeBean);
                    }
                });
    }

    private boolean checkParamWithLoginOrRegister(boolean isRegister, LoginOrRegisterParamBean param) {
        if (ObjectUtils.isEmpty(param.phone)) {
            mMutableLoginOrRegister.postValue(null);
            ToastUtils.showShort(StringUtils.getString(R.string.phone_not_empty));
            return false;
        }


        if (ObjectUtils.isEmpty(param.password)) {
            ToastUtils.showShort(StringUtils.getString(R.string.pwd_not_empty));
            mMutableLoginOrRegister.postValue(null);
            return false;
        }

        if (isRegister) {
            if (ObjectUtils.isEmpty(param.nickname)) {
                ToastUtils.showShort(StringUtils.getString(R.string.nickname_not_empty));
                mMutableLoginOrRegister.postValue(null);
                return false;
            }

            if (ObjectUtils.isEmpty(param.surePassword)) {
                ToastUtils.showShort(StringUtils.getString(R.string.pwd_not_empty));
                mMutableLoginOrRegister.postValue(null);
                return false;
            }
            if (!ObjectUtils.equals(param.password, param.surePassword)) {
                ToastUtils.showShort(StringUtils.getString(R.string.pwd_not_same));
                mMutableLoginOrRegister.postValue(null);
                return false;
            }
            if (param.code == 0) {
                mMutableLoginOrRegister.postValue(null);
                ToastUtils.showShort(StringUtils.getString(R.string.verity_code_not_empty));
                return false;
            }
        }

        return true;
    }

    public LiveData<LoginOrRegisterBean> getLoginOrRegister() {
        return mMutableLoginOrRegister;
    }
}
