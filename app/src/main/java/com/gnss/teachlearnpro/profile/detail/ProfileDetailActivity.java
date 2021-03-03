package com.gnss.teachlearnpro.profile.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UiMessageUtils;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.Contact;
import com.gnss.teachlearnpro.common.ui.BaseActivity;
import com.yalantis.ucrop.UCrop;

public class ProfileDetailActivity extends BaseActivity implements ActivityProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        int type = getIntent().getIntExtra(Contact.PROFILE_DETAIL_TYPE, -1);
        new ProfileDetailLogic(this, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtils.showShort("裁剪回调");
        //裁剪后的效果
        switch (requestCode) {
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    UiMessageUtils.getInstance().send(Contact.AVATAR_CORP_PATH,resultUri.getPath());
                }
                break;
            //错误裁剪的结果
            case UCrop.RESULT_ERROR:
                if (resultCode == RESULT_OK) {
                    final Throwable cropError = UCrop.getError(data);
                    ToastUtils.showShort(cropError.getMessage());
                }
                break;
        }
    }
}
