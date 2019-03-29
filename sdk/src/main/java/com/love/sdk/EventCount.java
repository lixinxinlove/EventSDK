package com.love.sdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.love.sdk.task.LeeTask;
import com.love.sdk.util.LocationUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by android on 2017/10/26.
 */

public class EventCount {

    private static final long TIMER_DELAY_IN_SECONDS = 20;  //60

    private static final String TAG = "EventCount";
    private static EventCount instance;
    private Context mContext;
    private Activity activity;
    private LeeTask leeTask;
    private ScheduledExecutorService timerService_;


    private EventCount(Context context) {
        this.mContext = context;
        leeTask = new LeeTask();
        timerService_ = Executors.newSingleThreadScheduledExecutor();
        timerService_.scheduleWithFixedDelay(leeTask, TIMER_DELAY_IN_SECONDS, TIMER_DELAY_IN_SECONDS, TimeUnit.SECONDS);
        Log.e(TAG, Thread.currentThread().getName());
    }


    private static void getInstance(Context context) {
        if (instance == null) {
            instance = new EventCount(context);
        }
    }

    public static void init(Context context) {
        getInstance(context);
    }

    public void setActivity(Activity act) {
        activity = act;
    }

    public void request() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
    }

    public void execute() {
        Toast.makeText(mContext, "获取权限后", Toast.LENGTH_LONG).show();
        LocationUtils.getInstance(mContext).getLocation();
        Location location = LocationUtils.getInstance(mContext).showLocation();
        if (location != null) {
            Log.e(TAG, location.getLatitude() + "--" + location.getLongitude());
        } else {
            Log.e(TAG, "没有获取位置信息");
        }
    }
}
