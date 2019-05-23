package com.admin.realize.mian.match.detail;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SkyTable {
    @Id
    private long id;
    private String text;
    @Generated(hash = 1965208347)
    public SkyTable(long id, String text) {
        this.id = id;
        this.text = text;
    }
    @Generated(hash = 985678164)
    public SkyTable() {
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
