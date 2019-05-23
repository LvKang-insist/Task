package com.admin.realize.mian.database.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class IndexTable {
    @Id
    private long index_id;
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String six ;
    private String seven;
    @Generated(hash = 196954891)
    public IndexTable(long index_id, String one, String two, String three,
            String four, String five, String six, String seven) {
        this.index_id = index_id;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
    }
    @Generated(hash = 709096806)
    public IndexTable() {
    }
    public long getIndex_id() {
        return this.index_id;
    }
    public void setIndex_id(long index_id) {
        this.index_id = index_id;
    }
    public String getOne() {
        return this.one;
    }
    public void setOne(String one) {
        this.one = one;
    }
    public String getTwo() {
        return this.two;
    }
    public void setTwo(String two) {
        this.two = two;
    }
    public String getThree() {
        return this.three;
    }
    public void setThree(String three) {
        this.three = three;
    }
    public String getFour() {
        return this.four;
    }
    public void setFour(String four) {
        this.four = four;
    }
    public String getFive() {
        return this.five;
    }
    public void setFive(String five) {
        this.five = five;
    }
    public String getSix() {
        return this.six;
    }
    public void setSix(String six) {
        this.six = six;
    }
    public String getSeven() {
        return this.seven;
    }
    public void setSeven(String seven) {
        this.seven = seven;
    }
   
}
