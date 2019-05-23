package com.admin.realize.mian.match;

import com.admin.realize.R;
import com.admin.task_core.ui.recycler.DataConvert;
import com.admin.task_core.ui.recycler.MultipleItemBean;

import java.util.ArrayList;

public class MatchDataConvert extends DataConvert {
    String[] str = {"天计划","周计划","月计划"};
    int[] image = {R.drawable.match_1,R.drawable.match_2,R.drawable.math_3};
    @Override
    public ArrayList<MultipleItemBean> convert() {
        for (int i = 0; i < str.length; i++) {
            DataBean db = new DataBean();
            db.id = i+"";
            db.name = str[i];
            db.image = image[i];
            db.isFav = false;
            MultipleItemBean bean = MultipleItemBean.builder()
                    .setItemType(MatchItemType.CARD)
                    .setFile(MatchFields.MATCH,db)
                    .build();
            data.add(bean);
        }
        return data;
    }
}
