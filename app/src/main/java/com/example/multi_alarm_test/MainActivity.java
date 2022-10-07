package com.example.multi_alarm_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_set_larm;
    TextView txt_timer;
    // on below line we are creating a variable.
    private TextView currentTV;
    RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // back = findViewById(R.id.backgamon);
        // on below line we are initializing our variables.
        currentTV = findViewById(R.id.idTVCurrent);


        Calendar now = Calendar.getInstance();
        //System.out.println(now.getTime());
        Log.d("time", now.getTime()+"");
        Log.d("time", now.get(Calendar.HOUR_OF_DAY)+"");



        Handler handler = new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z");
                String currentDateAndTime = sdf.format(new Date());
                currentTV.setText(currentDateAndTime);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnableCode);




        init();
    }


    public void init(){

        btn_set_larm = findViewById(R.id.button);
        // txt_timer = findViewById(R.id.textView);

        btn_set_larm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  setAlarm(1,9,0);
                setAlarm(1,9,1);
                setAlarm(1,9,2);
                setAlarm(1,9,3);
                setAlarm(1,9,4);
                setAlarm(1,9,5);*/
                setAlarm(1,2022,10,7,16,32,0);
                setAlarm(1,2022,10,7,16,33,0);
                setAlarm(1,2022,10,7,16,34,0);
                setAlarm(1,2022,10,7,16,35,0);
                setAlarm(1,2022,10,7,16,36,0);
                setAlarm(1,2022,10,7,16,37,0);
                setAlarm(1,2022,10,7,16,38,0);

            }
        });



    }
    PendingIntent pi;
    Intent intent;
    @SuppressLint("UnspecifiedImmutableFlag")
    public void setAlarm(int number,int Y_set,int M_set,int D_set,int h_set,int m_set,int s_set){



        AlarmManager am  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        List<Calendar> calendarList = new ArrayList<>();

        //calendar.set(Calendar.HOUR,0);
        // calendar.set(Calendar.MINUTE,0);
        // calendar.set(Calendar.MINUTE,0);
        calendar.set(Y_set,M_set-1,D_set,h_set,m_set,s_set);

        for (int i = 0; i < number; i++) {
            calendarList.add(calendar);
        }
        for (Calendar calendarItem:calendarList)
        {
            //each calender we will set alarm for it
            //calendarItem.add(CalendaR.GE);
            // calendarItem.add(Calendar.MONTH,M_set);
            // calendarItem.add(Calendar.DATE,D_set);
            // calendarItem.add(Calendar.HOUR,h_set);
            ///calendarItem.add(Calendar.MINUTE, m_set);


            String Y =  calendarItem.get(Calendar.YEAR) + "";
            String M = calendarItem.get(Calendar.MONTH) + "";
            String D = calendarItem.get(Calendar.DAY_OF_MONTH)+"";
            String h_ =  calendarItem.get(Calendar.HOUR) + "";
            String m_ = calendarItem.get(Calendar.MINUTE) + "";
            String s_ = calendarItem.get(Calendar.SECOND)+"";
            String name = Y+" : "+M+" : "+D+" : "+h_+" : "+m_+" : "+s_;
            String code = Y+""+M+""+D+""+h_+""+m_+""+s_;
           String String = java.lang.String.valueOf(calendarItem.getTime());
            Toast.makeText(this, name+" ", Toast.LENGTH_SHORT).show();


            int requestCode  = Integer.parseInt(code);
            intent = new Intent(this,MyAlarmReceiver.class);
            intent.putExtra("REQUEST_CODE",requestCode);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);

            //HERE EACH ALARM WE WILL SET EACH 'request code',incase you want to cancel alarm, you can use this request code
            //because i get time in milis to requestcode ,it is long so i must div by 1000 to get integer
            pi = PendingIntent.getBroadcast(this,requestCode,intent,PendingIntent.FLAG_ONE_SHOT);


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                am.setExact(AlarmManager.RTC_WAKEUP,calendarItem.getTimeInMillis(),pi);
            }else{
                am.setExact(AlarmManager.RTC_WAKEUP,calendarItem.getTimeInMillis(),pi);
            }
            Toast.makeText(this, "Alarm has been set", Toast.LENGTH_SHORT).show();
        }


    }


    @SuppressLint("UnspecifiedImmutableFlag")
    public void cancel_alarm(View view) {
        pi = PendingIntent.getBroadcast(this, 91, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if(pi != null) {
            alarmManager.cancel(pi);

        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public void check_if_existing(View view) {
        pi = PendingIntent.getBroadcast(this, 91, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            int requestCode = intent.getExtras().getInt("requestCode");
            Toast.makeText(this, "the code is "+requestCode, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }

    }
}
