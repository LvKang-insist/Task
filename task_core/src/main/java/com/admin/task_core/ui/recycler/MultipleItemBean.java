package com.admin.task_core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * @description: ${DESCRIPTION}
 */
public class MultipleItemBean implements MultiItemEntity {

    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();

    private final SoftReference<LinkedHashMap<Object, Object>> FILEDS_RETERENCE =
            new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);

    @Override
    public int getItemType() {
        return (int) FILEDS_RETERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    MultipleItemBean(LinkedHashMap<Object, Object> map) {
        FILEDS_RETERENCE.get().putAll(map);
    }

    public static MultipleItemBeanBuilder builder() {
        return new MultipleItemBeanBuilder();
    }

    public <T> T getField(Object field) {
        return (T) FILEDS_RETERENCE.get().get(field);
    }

    public LinkedHashMap<?, ?> getFields(MultipleFields imageUrl) {
        return FILEDS_RETERENCE.get();
    }

    public MultipleItemBean setField(Object key, Object value) {
        FILEDS_RETERENCE.get().put(key, value);
        return this;
    }
}
