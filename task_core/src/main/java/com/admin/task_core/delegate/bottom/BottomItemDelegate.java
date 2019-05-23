package com.admin.task_core.delegate.bottom;

import android.widget.Toast;

import com.admin.task_core.delegate.CoKeDelegate;

/**
  * @description: ${DESCRIPTION}
 */
public abstract class BottomItemDelegate extends CoKeDelegate {

    private static final long WAIT_TIEM = 2000L;
    private static long TOUCH_ITEM = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_ITEM < WAIT_TIEM){
            _mActivity.finish();
        }else {
            TOUCH_ITEM = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
