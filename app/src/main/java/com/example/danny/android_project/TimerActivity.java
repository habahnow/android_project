package com.example.danny.android_project;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by danny on 6/12/15.
 */
public class TimerActivity extends Activity {


    private Button weatherBut, todoBut, alarmBut;
    private Button start;
    private Button stop;

    EditText hourEditText;
    EditText minuteEditText;
    EditText secondEditText;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
        weatherBut = (Button) findViewById(R.id.weatherBut);
        todoBut = (Button) findViewById(R.id.To_DoBut);
        start = (Button) findViewById(R.id.start);
        stop= (Button) findViewById(R.id.stop);

        hourEditText = (EditText) findViewById(R.id.hour_edit_text);
        minuteEditText = (EditText) findViewById(R.id.minute_edit_text);
        secondEditText = (EditText) findViewById(R.id.second_edit_text);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTimer(view);
                Log.d("START","PRESSED");

            }
        });




    }

    public void startTimer(View v){
        int hour = Integer.parseInt(hourEditText.getText().toString());
        int minute = Integer.parseInt(minuteEditText.getText().toString());
        int second= Integer.parseInt(secondEditText.getText().toString());

        int totalSeconds = second;

        totalSeconds +=  minute * 60;
        totalSeconds += hour * 60 * 60;

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.SECOND, totalSeconds);

        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pi= PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , pi);

    }

    public void stopTimer(View v){
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pi= PendingIntent.getBroadcast(this, 0, intent, 0);

        //AlarmManager am = (AlarmManager) getSystemService(Con)

    }
}
