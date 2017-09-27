package com.example.clark.testbmob;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;

/**
 * Application
 * Created by wuxianglong on 2016/9/8.
 */
public class MyApplication extends Application {

    private static final String APPLICATION_ID = "b2405d64b23890fff400521e87f08558";
    private static final String BUGLY_ID = "e75747b1ac";

    BmobPushManager bmobPushManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, APPLICATION_ID);
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_ID, false);

        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();

        // 启动推送服务
        BmobPush.startWork(this);

        // 创建推送消息的对象
        bmobPushManager = new BmobPushManager();
    }
}
