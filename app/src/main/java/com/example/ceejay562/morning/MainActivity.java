package com.example.ceejay562.morning;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    private void setView(){
        //file manager to load all the files

        String[] values = new String[]{
                "Hello", "works", "Yeah","Hello",
                "works", "Yeah","Hello", "works", "Yeah","Hello", "works", "Yeah"};
        final ListView lv = (ListView)findViewById(R.id.listView);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                          android.R.layout.simple_list_item_1, values);
        lv.setAdapter(adapter);

        //image view
        Context context = null;
        View  footerView = (View) ((LayoutInflater) this.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        lv.addHeaderView(footerView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
        int position, long id) {

            if (position == 0){

            }
            // ListView Clicked item index
            int itemPosition     = position;

            // ListView Clicked item value
            String  itemValue    = (String) lv.getItemAtPosition(position);

            // Show Alert
            Toast.makeText(getApplicationContext(),
                    "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                    .show();

        }

        });



}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
