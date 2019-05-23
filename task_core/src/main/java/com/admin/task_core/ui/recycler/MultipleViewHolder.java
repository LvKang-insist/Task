package com.admin.task_core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @description: ${DESCRIPTION}
 */
public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
