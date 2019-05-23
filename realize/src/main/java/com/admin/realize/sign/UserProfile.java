package com.admin.realize.sign;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserProfile {
    @Id
    private String number;
    private String password;
    private String mibao;
    @Generated(hash = 358123280)
    public UserProfile(String number, String password, String mibao) {
        this.number = number;
        this.password = password;
        this.mibao = mibao;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public String getNumber() {
        return this.number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMibao() {
        return this.mibao;
    }
    public void setMibao(String mibao) {
        this.mibao = mibao;
    }

 
}
