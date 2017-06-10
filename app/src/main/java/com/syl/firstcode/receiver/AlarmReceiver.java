package com.syl.firstcode.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.syl.firstcode.utils.LogUtil;

/**
 * author   Bright
 * date     2017/6/10 12:40
 * desc
 * Receiver,接收AlarmManager通过PendingIntent发送的广播
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        System.out.println(msg);
        LogUtil.d(this.getClass().getSimpleName(), msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
