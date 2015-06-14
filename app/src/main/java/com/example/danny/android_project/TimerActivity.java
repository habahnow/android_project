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
        int hours = Integer.parseInt(hourEditText.getText().toString());
        int minutes = Integer.parseInt(minuteEditText.getText().toString());
        int seconds= Integer.parseInt(secondEditText.getText().toString());

        int totalSeconds = seconds;

        totalSeconds +=  minutes * 60;
        totalSeconds += hours * 60 * 60;

        Intent i = new Intent(getApplicationContext(), AlarmActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),333,i,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //getting current time and add 2 seconds in it
        //seconds should be set to the default or user set snooze value

        // display the snooze time, in minutes.
        Context context = getApplicationContext();

        int displayMinutes = seconds / 60;
        int displaySeconds = seconds % 60;
        displayMinutes += minutes % 60;
        int displayHours = minutes

        CharSequence text = "Alarm set for: " +  hour;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.SECOND, totalSeconds);

        //registering our pending intent with alarmmanager
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

    }

    public void stopTimer(View v){
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pi= PendingIntent.getBroadcast(this, 0, intent, 0);

        //AlarmManager am = (AlarmManager) getSystemService(Con)

    }
}
