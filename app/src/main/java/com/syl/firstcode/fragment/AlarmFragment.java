package com.syl.firstcode.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;
import com.syl.firstcode.service.AlarmService;

/**
 * Created by Bright on 2017/6/10.
 *
 * @Describe alarm机制, AlarmManager的使用
 * Alarm除了可以定时或者启动Service和BroadCastReceiver外,还可以启动Activity,不过如果定时启动Activity,让人多少觉得会
 * 有点突兀
 * @Called
 */

public class AlarmFragment extends BaseFragment implements View.OnClickListener {

    private View mRootView;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_alarm, null);
        return mRootView;
    }

    @Override
    public void initListener() {
        super.initListener();
        mRootView.findViewById(R.id.btn_intent_service).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_intent_receiver).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_intent_service:
                alarmService();
                break;
            case R.id.btn_intent_receiver:
                alarmReceiver();
                break;
            default:
                break;
        }
    }

    /**
     * 发送alarm机制的Service,定时或者即时启动一个Service
     */
    private void alarmService() {
        Intent intent = new Intent(getContext(), AlarmService.class);
        intent.putExtra("msg", "alarm msg_alarmService");
        PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        long triggerAtMillis = SystemClock.elapsedRealtime() + 5 * 1000;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * 发送alarm机制的广播,定时或者即时发送一个广播
     */
    private void alarmReceiver() {
        Intent intent = new Intent("MY_RECEIVER_CLOCK");
        intent.putExtra("msg", "alarm msg_alarmReceiver");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        long triggerAtMillis = SystemClock.elapsedRealtime() + 5 * 1000;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
    }
}
