package com.admin.task_core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
  * @description: RecyclerView 的分割线
 */
public class BaseDecoration extends DividerItemDecoration {
    private BaseDecoration(@ColorInt final int color, final int size){
        setDividerLookup(new DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return new Divider.Builder()
                        .color(color)
                        .size(size)
                        .build();
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .color(size)
                        .size(size)
                        .build();
            }
        });
    }

    public static BaseDecoration create(@ColorInt int color,int size){
        return new BaseDecoration(color,size);
    }
}
