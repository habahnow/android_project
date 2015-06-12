package com.example.danny.android_project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by danny on 6/12/15.
 */
public class TimerActivity extends Activity {

    private Button weatherBut, todoBut,alarmBut;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
        weatherBut = (Button) findViewById(R.id.weatherBut);
        todoBut = (Button) findViewById(R.id.To_DoBut);
    }
}
