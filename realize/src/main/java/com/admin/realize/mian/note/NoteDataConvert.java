package com.admin.realize.mian.note;

import com.admin.realize.mian.database.DatabaseManager;
import com.admin.realize.mian.note.table.EditTable;
import com.admin.realize.mian.note.table.EditTableDao;
import com.admin.task_core.ui.recycler.DataConvert;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoteDataConvert extends DataConvert {

    @Override
    public ArrayList<MultipleItemBean> convert() {
        EditTableDao editTableDao = DatabaseManager.getInstance().getDao().getEditTableDao();
        List<EditTable> editTables = editTableDao.loadAll();
        for (int i = 0; i < editTables.size(); i++) {
            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(NoteItemType.NOTE_ITEM)
                    .setFile(MultipleFields.ID,i)
                    .setFile(MultipleFields.TAG,editTables.get(i).getPosition())
                    .setFile(MultipleFields.SPAN_SIZE, 1)
                    .setFile(MultipleFields.TEXT, editTables.get(i).getText())
                    .setFile(MultipleFields.DATE, editTables.get(i).getDate())
                    .build();
            data.add(bean);
        }
        System.out.println(NoteDelegate.POS);
        Iterator<MultipleItemBean> iterator = data.iterator();
        while (iterator.hasNext()){
            MultipleItemBean bean = iterator.next();
            if ((int)bean.getField(MultipleFields.TAG) != NoteDelegate.POS){
                iterator.remove();
            }
        }
        return data;
    }


}
