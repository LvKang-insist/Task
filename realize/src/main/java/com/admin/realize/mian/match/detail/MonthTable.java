package com.admin.realize.mian.match.detail;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MonthTable {
    @Id
    private long id;
    private String text;
    @Generated(hash = 894789842)
    public MonthTable(long id, String text) {
        this.id = id;
        this.text = text;
    }
    @Generated(hash = 1524724144)
    public MonthTable() {
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
