package com.gnss.teachlearnpro.gate;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecommerce.common.ui.compent.MeBaseActivity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;
import com.gnss.teachlearnpro.gate.logic.GateLogic;

public class GateActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
//        setContentView(R.layout.fragment_group_entry);
        PermissionUtils.permission(PermissionConstants.STORAGE,PermissionConstants.LOCATION)
                .callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                new GateLogic(GateActivity.this);
            }

            @Override
            public void onDenied() {
                ToastUtils.showShort("必须授权才能正常使用");
                AppUtils.exitApp();
            }
        }).request();
    }


}
