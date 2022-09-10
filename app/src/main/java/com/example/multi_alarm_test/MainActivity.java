 package com.example.multi_alarm_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

    Button btn_set_larm;
    TextView txt_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    public void init(){

        btn_set_larm = findViewById(R.id.button);
        txt_timer = findViewById(R.id.textView);

        btn_set_larm.setOnClickListener(view -> setAlarm(5));

    }

    public void setAlarm(int number){

        AlarmManager am  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        List<Calendar> calendarList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            calendarList.add(calendar);
        }
        for (Calendar calendarItem:calendarList)
        {
            //each calender we will set alarm for it
            calendarItem.add(Calendar.SECOND,10);


            int requestCode  = (int) calendar.getTimeInMillis()/1000;
            Intent intent = new Intent(this,MyAlarmReceiver.class);
            intent.putExtra("REQUEST_CODE",requestCode);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            //HERE EACH ALARM WE WILL SET EACH 'request code',incase you want to cancel alarm, you can use this request code
            //because i get time in milis to requestcode ,it is long so i must div by 1000 to get integer
            PendingIntent pi = PendingIntent.getBroadcast(this,requestCode,intent,0);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendarItem.getTimeInMillis(),pi);
            }else{
                am.setExact(AlarmManager.RTC_WAKEUP,calendarItem.getTimeInMillis(),pi);
            }
            Toast.makeText(this, "Alarm has been set", Toast.LENGTH_SHORT).show();
        }


    }

}