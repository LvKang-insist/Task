package com.admin.task_core.util;


import com.admin.task_core.app.Coke;

/**
  * @description: ${DESCRIPTION}
 */
public class StatusBarHeight {
    /**
     * @return 返回 状态栏的 高度，以像素为单位
     */
    public static int getStaticBarHeight() {
        int result = 0;
        int resourceId = Coke.getAppContext().getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = Coke.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 根据手机的分辨率 从 px(像素)的单位 转换为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Coke.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨路 从 dp单位 转换为 px(像素)
     */
    private static int dip2px(float dipValue) {
        final float scale = Coke.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
