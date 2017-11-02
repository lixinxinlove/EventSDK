package com.love.sdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.love.sdk.activity.SingleActivity;
import com.love.sdk.task.LocationTask;
import com.love.sdk.util.LocationUtils;
import com.love.sdk.util.PermissionsUtil;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by android on 2017/10/26.
 */

public class EventCount {
    /**
     * How often onTimer() is called.
     */
    private static final long TIMER_DELAY_IN_SECONDS = 10;  //60


    private static final String TAG = "EventCount";
    private volatile static EventCount instance;
    private static Context mContext;
    private static Activity activity;
    private static Timer timer;
    private static LocationTask task;


    private ScheduledExecutorService timerService_;


    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                getLocation();
            }
        }
    };


    private EventCount(Context context) {
        this.mContext = context;

        timerService_ = Executors.newSingleThreadScheduledExecutor();
        timerService_.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                onTimer();
            }
        }, TIMER_DELAY_IN_SECONDS, TIMER_DELAY_IN_SECONDS, TimeUnit.SECONDS);

        Log.e(TAG, Thread.currentThread().getName());

    }

    private void onTimer() {
        Log.e(TAG, Thread.currentThread().getName());
    }

    public static EventCount getInstance(Context context) {
        if (instance == null) {
            instance = new EventCount(context);
        }
        return instance;
    }

    public static void init() {
        //getLocation();
        // timer = new Timer();
        // task = new LocationTask(handler);
        //timer.schedule(task, 0, 5000);
    }


    private static void getLocation() {
        if (PermissionsUtil.checkSinglePermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.e("EventeCount", "有权限");
            LocationUtils.getInstance(mContext).getLocation();
            Location location = LocationUtils.getInstance(mContext).showLocation();

            if (location != null) {
                Log.e(TAG, location.getLatitude() + "--" + location.getLongitude());
            } else {
                Log.e(TAG, "没有获取位置信息");
            }
        } else {
            Log.e("EventeCount", "没有权限");
            Intent intent = new Intent(mContext, SingleActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public static void setActivity(Activity act) {
        activity = act;
    }

    public static void request() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
    }

    public static void execute() {
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
