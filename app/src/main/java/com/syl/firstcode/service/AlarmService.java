package com.syl.firstcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.syl.firstcode.utils.LogUtil;

/**
 * author   Bright
 * date     2017/6/10 12:52
 * desc
 * 通过alarm机制启动的Service
 */
public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {
        public MyBinder() {
            logMsg();
        }
    }

    private void logMsg() {
        LogUtil.d(this.getClass().getSimpleName(),"msg_alarm_service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg = intent.getStringExtra("msg");
        LogUtil.d(this.getClass().getSimpleName(),msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(this.getClass().getSimpleName(),"msg_alarm_service=====onCreate()");
    }
}
