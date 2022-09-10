package com.example.multi_alarm_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int requestcode = intent.getIntExtra("REQUEST_CODE",-1);
        Toast.makeText(context, "Alarm fired with requestcode", Toast.LENGTH_SHORT).show();
    }
}
