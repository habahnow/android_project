package com.example.danny.android_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.Calendar;

/**
 * Created by danny on 5/22/15.
 */
public class Alarm_activity extends ActionBarActivity{
    private MediaPlayer mp;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_activity);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();

    }

    public void dismiss(View v){
        mp.stop();
        mp.release();



    }

    public void snooze(View v){
        mp.stop();
        mp.release();

        Intent i = new Intent(getApplicationContext(), Alarm_activity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),333,i,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //getting current time and add 5 seconds in it
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 2);

        //registering our pending intent with alarmmanager
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

    }
}
