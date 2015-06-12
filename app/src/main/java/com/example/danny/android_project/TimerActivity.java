package com.example.danny.android_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by danny on 6/12/15.
 */
public class TimerActivity extends Activity {

    private Button weatherBut, todoBut;
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

        hourEditText = (EditText) findViewById(R.id.hour_edit_text);
        minuteEditText = (EditText) findViewById(R.id.minute_edit_text);
        secondEditText = (EditText) findViewById(R.id.second_edit_text);

    }

    public void startTimer(View v){

    }

    public void stopTimer(View v){

    }
}
