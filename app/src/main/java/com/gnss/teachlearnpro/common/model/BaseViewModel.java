package com.gnss.teachlearnpro.common.model;

import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ToastUtils;
import com.zhouyou.http.exception.ApiException;

public class BaseViewModel extends ViewModel {

    public void tipError(ApiException e, String msg) {
        ToastUtils.showShort(msg + e.getMessage() + "\n错误码:" + e.getCode());
    }
}
