package com.admin.realize.mian.note;

import com.admin.task_core.ui.recycler.DataConvert;
import com.admin.task_core.ui.recycler.MultipleFields;
import com.admin.task_core.ui.recycler.MultipleItemBean;

import java.util.ArrayList;

public class NoteSortConvert extends DataConvert {

    String[] str = {"疑问","英语","数学","物理","化学","生物","历史","地理","语文"};

    @Override
    public ArrayList<MultipleItemBean> convert() {
        for (int i = 0; i < str.length; i++) {
            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(NoteItemType.NOTE_SORT)
                    .setFile(MultipleFields.ID,i)
                    .setFile(MultipleFields.TAG,false)
                    .setFile(MultipleFields.TEXT,str[i])
                    .build();
            if (i ==0 ){
                bean.setField(MultipleFields.TAG,true);
            }
            data.add(bean);

        }
        return data;
    }
}
