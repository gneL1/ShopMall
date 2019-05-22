package com.example.shopmall;

import android.app.Application;
import android.content.Context;

//一定要记得在xml中声明Application子类
public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }
    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }

//    public static Context getContext() {
//        return mContext;
//    }
//
//    private static Context mContext;
//
//    //软件一运行就有
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        this.mContext = this;
//    }
}
