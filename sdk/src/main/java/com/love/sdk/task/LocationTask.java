package com.love.sdk.task;

import android.os.Handler;

import java.util.TimerTask;

/**
 * Created by android on 2017/10/26.
 */

public class LocationTask extends TimerTask {

    private Handler handler;

    public LocationTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(1);
    }

}
