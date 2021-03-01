package com.gnss.teachlearnpro.common.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.ecommerce.common.ui.compent.MeBaseActivity;
import com.ecommerce.melibrary.log.MeLog;
import com.gnss.teachlearnpro.R;

public class BaseActivity extends MeBaseActivity {



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断当前按键是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            FragmentManager fragmentManager = getSupportFragmentManager();
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
                MeLog.e("backStack tag:"+tag);
//                if ("fragment1".equals(tag)) {
//                    // 设置首页选中
//                    rb_home.setChecked(true);
//                } else if ("fragment2".equals(tag)) {
//                    // 设置购物车的tag
//                    rb_cart.setChecked(true);
//                } else if ("fragment3".equals(tag)) {
//                    rb_category.setChecked(true);
//                } else if ("fragment4".equals(tag)) {
//                    rb_personal.setChecked(true);
//                }
            } else {
                //回退栈中只剩一个时,退出应用
                finish();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheMemoryUtils.getInstance().clear();
    }
}
