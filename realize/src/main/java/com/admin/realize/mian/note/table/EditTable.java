package com.admin.realize.mian.note.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EditTable {
    @Id()
    private long id;
    private int position;
    private String text;
    private String date;
    @Generated(hash = 577832909)
    public EditTable(long id, int position, String text, String date) {
        this.id = id;
        this.position = position;
        this.text = text;
        this.date = date;
    }
    @Generated(hash = 1295383632)
    public EditTable() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
  
  

}
