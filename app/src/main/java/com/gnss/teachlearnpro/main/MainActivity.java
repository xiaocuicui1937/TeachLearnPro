package com.gnss.teachlearnpro.main;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ecommerce.common.ui.compent.MeBaseActivity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

public class MainActivity extends MeBaseActivity implements ActivityProvider {
    MainActivityLogic mainActivityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityLogic = new MainActivityLogic(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mainActivityLogic.onSaveInstanceState(outState);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new XPopup.Builder(this)
                    .asConfirm(StringUtils.getString(R.string.quit_tip), StringUtils.getString(R.string.quit_content), new OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            AppUtils.exitApp();
                        }
                    }, () -> {
                        //nothing
                    }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}