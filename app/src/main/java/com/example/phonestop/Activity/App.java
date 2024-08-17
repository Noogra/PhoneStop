package com.example.phonestop.Activity;

import android.app.Application;

import com.example.phonestop.Data.DataManager;
import com.example.phonestop.Utilities.ImageLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.initImageLoader(this);
        DataManager.initDataManager(this);
    }
}
