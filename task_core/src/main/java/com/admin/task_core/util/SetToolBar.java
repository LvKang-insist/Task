package com.admin.task_core.util;

import android.support.v7.widget.Toolbar;

/**
  * @description: 调整 状态栏
 */
public class SetToolBar {
    public static void setToolBar(Toolbar mToolbar) {
        int statusBar = StatusBarHeight.getStaticBarHeight();
        mToolbar.setPadding(0, statusBar, 0, 20);
    }
}
