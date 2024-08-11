package com.example.phonestop;

import android.app.Application;

import com.example.phonestop.Utilities.ImageLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.initImageLoader(this);
    }
}
