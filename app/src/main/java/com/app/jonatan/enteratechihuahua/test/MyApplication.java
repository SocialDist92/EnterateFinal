package com.app.jonatan.enteratechihuahua.test;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jonatan on 06/05/2016.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }


}
