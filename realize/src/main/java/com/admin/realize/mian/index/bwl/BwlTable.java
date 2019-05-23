package com.admin.realize.mian.index.bwl;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BwlTable {

    @Id
    private long id ;
    private String text ;
    @Generated(hash = 224163576)
    public BwlTable(long id, String text) {
        this.id = id;
        this.text = text;
    }
    @Generated(hash = 2141837964)
    public BwlTable() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
