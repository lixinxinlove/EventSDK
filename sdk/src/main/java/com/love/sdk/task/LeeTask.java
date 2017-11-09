package com.love.sdk.task;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by android on 2017/11/2.
 */

public class LeeTask implements Runnable {

    private static final String TAG = "LeeTask";

    @Override
    public void run() {

        Log.e(TAG, Thread.currentThread().getName());
        String service = "http://www.baidu.com";
        URL url;
        HttpURLConnection conn;
        try {
            url = new URL(service);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            String msg = conn.getRequestMethod();
            Log.e(TAG, msg + "--code=" + code);
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String result = new String(toByteArray(is), "UTF-8");
                Log.e("URL百度", result);// 响应代码 200表示成功
                is.close();
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
