package com.example.danny.android_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Main_activity2 extends ActionBarActivity {

    private Button weatherbut;
    private Button alarmBut;
    private Button checkBut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity2);

        weatherbut=(Button) findViewById(R.id.weatherBut);
        weatherbut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                WeatherFragment hello = new WeatherFragment();
                fragmentTransaction.replace(R.id.fragment_container, hello);
                fragmentTransaction.commit();
            }
        });
        alarmBut=(Button) findViewById(R.id.alarmBut);
        alarmBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //WeatherFragment hello = new WeatherFragment();
                //fragmentTransaction.replace(R.id.fragment_container, hello);
                fragmentTransaction.commit();
            }
        });
        checkBut=(Button) findViewById(R.id.CheckBut);
        checkBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //WeatherFragment hello = new WeatherFragment();
                //fragmentTransaction.replace(R.id.fragment_container, hello);
                fragmentTransaction.commit();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
