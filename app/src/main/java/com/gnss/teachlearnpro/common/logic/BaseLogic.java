package com.gnss.teachlearnpro.common.logic;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Size;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ecommerce.melibrary.log.MeLog;
import com.ecommerce.melibrary.util.MeDisplayUtil;
import com.gnss.teachlearnpro.R;
import com.gnss.teachlearnpro.common.ui.ActivityProvider;
import com.gnss.teachlearnpro.common.ui.BaseActivity;
import com.gnss.teachlearnpro.common.ui.FragmentProvider;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

public class BaseLogic {
    private ZLoadingDialog zLoadingDialog;



    /**
     * 加载fragment
     */
    public void loadFragment(ActivityProvider provider, Fragment fragment, int containerId, String tag) {
//        FrameLayout rootLayout = mProvider.findViewById(R.id.root_profile_detail);
        FragmentTransaction ft = provider.getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(tag);
        ft.replace(containerId, fragment);
        ft.commit();
    }

    public void replaceFragment(Fragment destFragment, int containerId) {
        AppCompatActivity act = (AppCompatActivity) ActivityUtils.getTopActivity();
        FragmentTransaction ft = act.getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, destFragment);
        ft.commit();
    }


    protected void setTitle(FragmentProvider provider, String title, AppCompatActivity activity) {
        SuperTextView stvTitle = provider.getMineView().findViewById(R.id.stv_common_title);

        stvTitle.setLeftIcon(ContextCompat.getDrawable(stvTitle.getContext(), R.drawable.ic_left_arrow));

        ClickUtils.expandClickArea(stvTitle, SizeUtils.sp2px(20));
        ClickUtils.applyGlobalDebouncing(stvTitle, view -> ActivityUtils.finishActivity(activity));
        stvTitle.setCenterString(title);

        stvTitle.getLeftIconIV().setVisibility(showBack() ? View.VISIBLE : View.GONE);
    }

    protected void setTitle(String title) {
        BaseActivity activity = (BaseActivity) ActivityUtils.getTopActivity();
        SuperTextView stvTitle = activity.findViewById(R.id.ll_toolbar);
        if (stvTitle==null){
            stvTitle = activity.findViewById(R.id.stv_common_title);
        }

        stvTitle.setLeftIcon(ContextCompat.getDrawable(stvTitle.getContext(), R.drawable.ic_left_arrow));
        ClickUtils.expandClickArea(stvTitle, SizeUtils.sp2px(20));
        ClickUtils.applyGlobalDebouncing(stvTitle, view -> ActivityUtils.finishActivity(activity));

        stvTitle.setCenterString(title);

        stvTitle.getLeftIconIV().setVisibility(showBack() ? View.VISIBLE : View.GONE);
    }

    protected boolean showBack() {
        return true;
    }

    /**
     * 显示加载动画
     *
     * @param msg 提示信息
     */
    public void showLoading(String msg) {
        if (zLoadingDialog == null) {
            zLoadingDialog = new ZLoadingDialog(ActivityUtils.getTopActivity());
        }
        zLoadingDialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLUE)//颜色
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setHintText(msg)
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.BLUE)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
//                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .show();
    }

    /**
     * 关闭加载动画
     */
    public void hideLoading() {
        if (zLoadingDialog != null) {
            zLoadingDialog.dismiss();
            zLoadingDialog = null;
        }
    }


    public void fixContent(ViewGroup rootLayout, Resources resources, int height) {
        rootLayout.setPadding(0, 0, 0, MeDisplayUtil.dp2px(height, resources));
        rootLayout.setClipToPadding(false);
    }

    public void fixContent(ViewGroup rootLayout, Resources resources) {
        fixContent(rootLayout, resources, 56);
    }

    public void finishFragmentToActivity(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        // 获取当前回退栈中的Fragment个数
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        // 判断当前回退栈中的fragment个数,
        if (backStackEntryCount > 1) {
            // 立即回退一步
            fragmentManager.popBackStackImmediate();
            // 获取当前退到了哪一个Fragment上,重新获取当前的Fragment回退栈中的个数
            FragmentManager.BackStackEntry backStack = fragmentManager
                    .getBackStackEntryAt(fragmentManager
                            .getBackStackEntryCount() - 1);
            // 获取当前栈顶的Fragment的标记值
            String tag = backStack.getName();
            // 判断当前是哪一个标记
            MeLog.e("backStack tag:" + tag);
        } else {
            //回退栈中只剩一个时,退出应用
            activity.finish();
        }
    }
}
