package com.example.clark.testbmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by clark on 17/6/19.
 */
public class News extends BmobObject {
    private String title;
    private String content;
    private String userid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
