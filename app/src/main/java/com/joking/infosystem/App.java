package com.joking.infosystem;
/*
 * App     2017-04-08
 * Copyright (c) 2017 JoKing All right reserved.
 */

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePal;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */
public class App extends Application {
    // 第一行代码中写法
    private static Context mContext;
    private static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        LitePal.initialize(this);

        mRefWatcher = LeakCanary.install(this);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 4.0以下因无ActivityLifecycleCallbacks，须手动添加
     *
     * Activity onDestory()中添加：
     * App.getRefWatcher().watch(this);
     *
     * @return RefWatcher
     */
    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }
}