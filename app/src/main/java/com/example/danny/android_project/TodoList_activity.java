package com.example.danny.android_project;

import com.example.danny.android_project.db.Contract;
import com.example.danny.android_project.db.DBHelper;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import android.view.View;
import android.app.AlertDialog;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class TodoList_activity extends ListActivity {
    public ListAdapter listAdapter;
    public DBHelper helper;
    private Button weatherBut,alarmBut, TimerBut;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity);

        weatherBut = (Button) findViewById(R.id.weatherBut);

        alarmBut=(Button) findViewById(R.id.alarmBut);
        weatherBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TodoList_activity.this,WeatherActivity.class); //like an envelope where it's starting then where its going to g
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });
        alarmBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TodoList_activity.this,MainActivity.class); //like an envelope where it's starting then where its going to go.
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });

        TimerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TodoList_activity.this, TimerActivity.class); //like an envelope where it's starting then where its going to go.
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });

        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.to_do_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     return true;
    }

    private void update() {
        helper = new DBHelper(TodoList_activity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(Contract.TABLE_NAME,
                new String[]{Contract.Columns._ID, Contract.Columns.TASK},
                null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{Contract.Columns.TASK},
                new int[]{R.id.taskTextView},
                0
        );

        this.setListAdapter(listAdapter);
    }

    public void DoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                Contract.TABLE_NAME,
                Contract.Columns.TASK,
                task);


        helper = new DBHelper(TodoList_activity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        update();
    }

    public void AddButtonClick(View view) {

        switch (R.id.add_task) {
            case R.id.add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a task");
                builder.setMessage("What would you like to do?");
                final EditText input = new EditText(this);
                builder.setView(input);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = input.getText().toString();

                        helper = new DBHelper(TodoList_activity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues cv = new ContentValues();

                        cv.clear();
                        cv.put(Contract.Columns.TASK, task);

                        db.insertWithOnConflict(Contract.TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
                        update();
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.create().show();


        }


    }


    }
