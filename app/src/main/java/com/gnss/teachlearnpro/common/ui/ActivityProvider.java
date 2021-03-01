package com.gnss.teachlearnpro.common.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

public interface ActivityProvider {
    <T extends View> T findViewById(@IdRes int id);

    Resources getResources();

    FragmentManager getSupportFragmentManager();

    String getString(@StringRes int resId);

    Lifecycle getLifecycle();

    Intent getIntent();
}
