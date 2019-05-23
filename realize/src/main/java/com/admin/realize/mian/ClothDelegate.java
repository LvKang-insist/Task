package com.admin.realize.mian;

import android.graphics.Color;

import com.admin.realize.mian.index.IndexDelegate;
import com.admin.realize.mian.match.MatchDelegate;
import com.admin.realize.mian.note.NoteDelegate;
import com.admin.realize.mian.personal.PersonalDelegate;
import com.admin.task_core.delegate.bottom.BaseBottomDelegate;
import com.admin.task_core.delegate.bottom.BottomFactory;
import com.admin.task_core.delegate.bottom.BottomItemDelegate;
import com.admin.task_core.delegate.bottom.BottomTabBean;

import java.util.LinkedHashMap;


/**
 * @description: ${DESCRIPTION}
 */
public class ClothDelegate extends BaseBottomDelegate {

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setTabColor() {
        return Color.parseColor("#FF4686F3");
    }

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(BottomFactory factory) {
        factory.addItem(new BottomTabBean("{fa-university}","课表"),new IndexDelegate());
        factory.addItem(new BottomTabBean("{fa-unsorted}","学习计划"),new MatchDelegate());
        factory.addItem(new BottomTabBean("{fa-photo}","笔记"),new NoteDelegate());
        factory.addItem(new BottomTabBean("{fa-user}","我的"),new PersonalDelegate());
        return factory.build();
    }
}
