package com.odenktools.netzmeodenktools;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import io.paperdb.Paper;
import timber.log.Timber;

public class BaseApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static volatile Context applicationContext;

    @SuppressLint("StaticFieldLeak")
    public static volatile Handler applicationHandler;

    public BaseApp() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
        Paper.init(applicationContext);
    }
}


