package com.admin.realize.mian.note.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SortTable {
    @Id
    private long id;
    private String name;
    @Generated(hash = 1506541905)
    public SortTable(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 847306846)
    public SortTable() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
