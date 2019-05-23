package com.admin.task_core.ui.recycler;

import java.util.ArrayList;

/**
 * @description: ${DESCRIPTION}
 */
public abstract class DataConvert {

    public final ArrayList<MultipleItemBean> data = new ArrayList<>();

    public String json = null;

    public void setJson(String json) {
        this.json = json;
    }

    public abstract ArrayList<MultipleItemBean> convert();
}
