package com.gnss.teachlearnpro.main;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.ecommerce.common.ui.compent.MeBaseActivity;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;

public class MainActivity extends MeBaseActivity implements ActivityProvider {
    MainActivityLogic mainActivityLogic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityLogic = new MainActivityLogic(this,savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mainActivityLogic.onSaveInstanceState(outState);
    }
}