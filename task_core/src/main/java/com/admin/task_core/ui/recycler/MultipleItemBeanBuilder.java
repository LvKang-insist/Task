package com.admin.task_core.ui.recycler;

import java.util.LinkedHashMap;

/**
  * @description: ${DESCRIPTION}
 */
public class MultipleItemBeanBuilder {

    private final LinkedHashMap<Object, Object> FILES = new LinkedHashMap<>();

    public MultipleItemBeanBuilder setFile(Object key, Object value) {
        FILES.put(key, value);
        return this;
    }

    public MultipleItemBeanBuilder setFiles(LinkedHashMap<Object, Object> files) {
        FILES.putAll(files);
        return this;
    }

    public MultipleItemBeanBuilder setItemType(int itemType){
        FILES.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public MultipleItemBean build(){
        return new MultipleItemBean(FILES);
    }

}
