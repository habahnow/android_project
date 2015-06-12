package com.example.ceejay562.morning;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import java.util.Random;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlarmScreen extends Activity {
	char[] operators = {'+', '-', '*' };
	public final String TAG = this.getClass().getSimpleName();

	private WakeLock mWakeLock;
	private MediaPlayer mPlayer;

	private static final int WAKELOCK_TIMEOUT = 60 * 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Setup layout
		this.setContentView(R.layout.activity_alarm_screen);

		String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
		int timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
		int timeMinute = getIntent().getIntExtra(AlarmManagerHelper.TIME_MINUTE, 0);
		String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);
		
		//TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
		//tvName.setText(name);

        //Random ran = new Random();
        //int adderOne = ran.nextInt(100);
        //char operator = ran.nextInt();
        //int adderTwo = ran.nextInt(100);

		TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
		tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));
		
		Button dismissButton = (Button) findViewById(R.id.alarm_screen_button);
        //answer
        final int answer = handleOperation();
        boolean killer = false;
        //while(!killer){
           endBroadcaster(tone, dismissButton );


	}

    private boolean endBroadcaster(String tone, Button dismissButton ){

        //answer
        final int answer = handleOperation();
        boolean killer = false;

            dismissButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    EditText ed = (EditText) findViewById(R.id.editText);
                    //int useranSwer = Integer.parseInt(ed.toString());
                    //if (useranSwer == answer) {
                        mPlayer.stop();
                        finish();
                   // }

                }
            });
         //if (mPlayer.isPlaying()){
            //return false;
         //}
        //Play alarm tone
        mPlayer = new MediaPlayer();
        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        //WakeLock
        Runnable releaseWakelock = new Runnable() {

            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                }
            }
        };

        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);

        return true;
    }

	private int handleOperation(){
        Random ran = new Random();
        int adderOne = ran.nextInt(100) + 100;
        int adderTwo = ran.nextInt(100);
        int operator = ran.nextInt(3);

        TextView tv = (TextView)findViewById(R.id.textView);
        String statement;
        if(operator == 0) {
            statement = adderOne + " + " + adderTwo;
            tv.setText(statement);
            return adderOne + adderTwo;
        }
        else if(operator == 1){
            statement = adderOne + " - " + adderTwo;
            tv.setText(statement);
            return adderOne - adderTwo;
        }
        else{
            statement = adderOne + " * " + adderTwo;
            tv.setText(statement);
            return adderOne * adderTwo;
        }
    }
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		// Set the window to keep screen on
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		// Acquire wakelock
		PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		if (mWakeLock == null) {
			mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
		}

		if (!mWakeLock.isHeld()) {
			mWakeLock.acquire();
			Log.i(TAG, "Wakelock aquired!!");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mWakeLock != null && mWakeLock.isHeld()) {
			mWakeLock.release();
		}
	}
}
