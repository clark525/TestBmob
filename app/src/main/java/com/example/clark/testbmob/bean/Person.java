package com.example.clark.testbmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by clark on 17/6/19.
 */
public class Person extends BmobObject {
    private String username;
    private String password;
    private String address;


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
