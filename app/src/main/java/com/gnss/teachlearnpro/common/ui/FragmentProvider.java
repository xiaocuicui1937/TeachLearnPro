package com.gnss.teachlearnpro.common.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.ecommerce.common.ui.compent.MeBaseFragment;

public interface FragmentProvider {
    @Nullable
     View getMineView();

    @NonNull
     LifecycleOwner getViewLifecycleOwner();

    String getString(@StringRes int resId);

    Resources getResources();

    FragmentManager getParentFragmentManager();

    Lifecycle getLifecycle();

    FragmentActivity getActivity();

    FragmentManager getChildFragmentManager();

    Bundle getArguments();

}
