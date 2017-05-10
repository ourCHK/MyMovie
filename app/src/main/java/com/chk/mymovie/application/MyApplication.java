package com.chk.mymovie.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by chk on 17-4-6.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
