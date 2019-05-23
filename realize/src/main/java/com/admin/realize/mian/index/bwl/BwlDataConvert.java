package com.admin.realize.mian.index.bwl;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.task_core.ui.recycler.DataConvert;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;

import java.util.ArrayList;
import java.util.List;

public class BwlDataConvert extends DataConvert {
    @Override
    public ArrayList<MultipleItemBean> convert() {
        List<BwlTable> bwlTables = DatabaseManager.getInstance().getDao().getBwlTableDao().loadAll();
        for (int i = 0; i < bwlTables.size(); i++) {
            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(BwlItemType.ITEM_BWM)
                    .setFile(MultipleFields.TEXT,bwlTables.get(i).getText())
                    .setFile(MultipleFields.ID,bwlTables.get(i).getId())
                    .setFile(MultipleFields.SPAN_SIZE,1)
                    .build();
            data.add(bean);
        }
        System.out.println("-------解析-------"+data.size());
        return data;
    }
}
