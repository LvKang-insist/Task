package com.admin.task_core.delegate.bottom;

import java.util.LinkedHashMap;

/**
 * @description: ${DESCRIPTION}
 */
@SuppressWarnings("ALL")
public class BottomFactory {

    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    private BottomFactory() {
    }
    public static BottomFactory builder() {
        return new BottomFactory();
    }

    public void addItem(BottomTabBean tab, BottomItemDelegate delegate) {
        ITEMS.put(tab, delegate);
    }

    public void addAll(LinkedHashMap<BottomTabBean, BottomItemDelegate> bottom) {
        ITEMS.putAll(bottom);
    }

    public LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
