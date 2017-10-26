package com.love.eventsdk;

import android.app.Application;

import com.love.sdk.EventCount;

/**
 * Created by android on 2017/10/26.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EventCount.getInstance(this).init();
    }
}
