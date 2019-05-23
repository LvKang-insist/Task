package com.admin.realize.mian.index;

import android.graphics.Color;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.database.table.DaoSession;
import com.admin.realize.mian.database.table.IndexTable;
import com.admin.task_core.ui.recycler.DataConvert;
import com.admin.task_core.ui.recycler.ItemType;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @description: ${DESCRIPTION}
 */
public class IndexDataConvert extends DataConvert {

    String[] date = {"月份", "周一", "周二", "周三", "周四", "周五","周六","周日"};

    @Override
    public ArrayList<MultipleItemBean> convert() {
        Calendar instance = Calendar.getInstance();
        int month = instance.get(Calendar.MONTH);
        date[0] = 1+month+"月";
        for (int i = 0; i < date.length; i++) {
            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(ItemType.DATE)
                    .setFile(MultipleFields.ID, i)
                    .setFile(MultipleFields.SPAN_SIZE, 1)
                    .setFile(MultipleFields.TEXT, date[i])
                    .build();
            data.add(bean);
        }
        /*
         * 获取 数据库中的课表数据
         */
        DaoSession dao = DatabaseManager.getInstance().getDao();
        List<IndexTable> indexTables = dao.getIndexTableDao().loadAll();
        for (int i = 0; i < indexTables.size(); i++) {
            IndexTable indexTable = indexTables.get(i);
            String[] str = {String.valueOf(indexTable.getIndex_id()), indexTable.getOne(),
                    indexTable.getTwo(), indexTable.getThree(), indexTable.getFour(),
                    indexTable.getFive(),indexTable.getSix(),indexTable.getSeven()};
            for (int j = 0; j < str.length; j++) {
                if (j == 0) {
                    MultipleItemBean bean = MultipleItemBean.builder()
                            .setItemType(ItemType.CURRICULUM)
                            .setFile(MultipleFields.ROW, i)
                            .setFile(MultipleFields.RANK, j)
                            .setFile(MultipleFields.SPAN_SIZE, 1)
                            .setFile(MultipleFields.COLOR, Color.TRANSPARENT)
                            .setFile(MultipleFields.TEXT, str[j])
                            .build();
                    data.add(bean);
                } else {
                    MultipleItemBean bean = MultipleItemBean.builder()
                            .setItemType(ItemType.CURRICULUM)
                            .setFile(MultipleFields.ROW, i)
                            .setFile(MultipleFields.RANK, j)
                            .setFile(MultipleFields.SPAN_SIZE, 1)
                            .setFile(MultipleFields.COLOR, Color.parseColor("#EDEDDC"))
                            .setFile(MultipleFields.TEXT, str[j])
                            .build();
                    data.add(bean);
                }
            }
        }
        return data;
    }
}
