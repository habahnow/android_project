package com.example.danny.android_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by danny on 5/22/15.
 */
public class Alarm_activity extends ActionBarActivity{
    private MediaPlayer mp;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_activity);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mp = MediaPlayer.create(getApplicationContext(), notification);
        mp.start();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        /*
         Replaces the default 'Back' button action with a message so that the user can not
         accidentally exit the alarm.
        */
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            Context context = getApplicationContext();
            CharSequence text = "Press either snooze or dismiss.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        return true;
    }

    public void dismiss(View v){
        mp.stop();
        mp.release();

        this.finish();



    }

    public void snooze(View v){
        mp.stop();
        mp.release();

        Intent i = new Intent(getApplicationContext(), Alarm_activity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),333,i,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //getting current time and add 2 seconds in it
        //seconds should be set to the default or user set snooze value
        int seconds = 2;

        // display the snooze time, in minutes.
        Context context = getApplicationContext();
        CharSequence text = "Alarm snoozed for " + seconds / 60;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.SECOND, seconds);

        //registering our pending intent with alarmmanager
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);

        this.finish();

    }
}
