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
    private volatile static EventCount instance;
    private static Context mContext;
    private static Activity activity;
   // private static Timer timer;
   // private static LocationTask task;
    private LeeTask leeTask;


    private static ScheduledExecutorService timerService_;

//    private static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 1) {
//            }
//        }
//    };


    private EventCount(Context context) {
        this.mContext = context;
        leeTask = new LeeTask();
        timerService_= Executors.newSingleThreadScheduledExecutor();
        timerService_.scheduleWithFixedDelay(leeTask, TIMER_DELAY_IN_SECONDS, TIMER_DELAY_IN_SECONDS, TimeUnit.SECONDS);
        Log.e(TAG, Thread.currentThread().getName());
    }

//    private void onTimer() {
//        Log.e(TAG, Thread.currentThread().getName());
//        handler.sendEmptyMessage(1);
//
//        String data = "&tz=" + DeviceInfo.getTimezoneOffset()
//                + "&Device=" + DeviceInfo.getDevice()
//                + "&Locale=" + DeviceInfo.getLocale()
//                + "&AppVersion=" + DeviceInfo.getAppVersion(mContext)
//                + "&Density=" + DeviceInfo.getDensity(mContext)
//                + "&metrics=" + DeviceInfo.getMetrics(mContext);
//        Log.e(TAG, data);
//    }

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


//    private static void getLocation() {
//        if (PermissionsUtil.checkSinglePermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Log.e("EventeCount", "有权限");
//            LocationUtils.getInstance(mContext).getLocation();
//            Location location = LocationUtils.getInstance(mContext).showLocation();
//
//            if (location != null) {
//                Log.e(TAG, location.getLatitude() + "--" + location.getLongitude());
//            } else {
//                Log.e(TAG, "没有获取位置信息");
//            }
//        } else {
//            Log.e("EventeCount", "没有权限");
//            Intent intent = new Intent(mContext, SingleActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(intent);
//        }
//    }

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
