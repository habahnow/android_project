package com.example.ceejay562.morning;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.ceejay562.morning.AlarmContract.Alarm;

public class AlarmDBHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";
    // SQLiteDatabase db = this.getReadableDatabase();
	private static final String SQL_CREATE_ALARM = "create" + Alarm.TABLE_NAME + " (" +
			Alarm._ID + " integer primarykey autoincrement," +
			Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS + " TEXT," +
            Alarm.COLUMN_NAME_ALARM_NAME + " text," +
			Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY + " boolean," +
			Alarm.COLUMN_NAME_ALARM_TONE + " text," +
            Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " integer," +
            Alarm.COLUMN_NAME_ALARM_TIME_MINUTE + " integer," +
			Alarm.COLUMN_NAME_ALARM_ENABLED + "boolean" +
	    " )";
	
	private static final String SQL_DELETE_ALARM =
		    "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;
    
	public AlarmDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ALARM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
	}
	
	private AlarmModel populateModel(Cursor c) {
		AlarmModel model = new AlarmModel();
		model.id = c.getLong(c.getColumnIndex(Alarm._ID));
		model.name = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_NAME));
		model.timeHour = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_HOUR));
		model.timeMinute = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE));
		model.repeatWeekly = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY)) == 0 ? false : true;
		model.alarmTone = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TONE)) != "" ? Uri.parse(c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TONE))) : null;
		model.isEnabled = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_ENABLED)) == 0 ? false : true;
		
		String[] repeatingDays = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS)).split(",");
		for (int i = 0; i < repeatingDays.length; ++i) {
			model.setRepeatingDay(i, repeatingDays[i].equals("false") ? false : true);
		}
		
		return model;
	}

    public long createAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);
    }

	
	private ContentValues populateContent(AlarmModel model) {
		ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_ALARM_NAME, model.name);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_HOUR, model.timeHour);
        values.put(Alarm.COLUMN_NAME_ALARM_TIME_MINUTE, model.timeMinute);
        values.put(Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY, model.repeatWeekly);
        values.put(Alarm.COLUMN_NAME_ALARM_TONE, model.alarmTone != null ? model.alarmTone.toString() : "");
        values.put(Alarm.COLUMN_NAME_ALARM_ENABLED, model.isEnabled);
        
        String repeatingDays = "";
        for (int i = 0; i < 7; ++i) {
        	repeatingDays += model.getRepeatingDay(i) + ",";
        }
        values.put(Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS, repeatingDays);
        
        return values;
	}

    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(Alarm.TABLE_NAME, Alarm._ID + " = ?", new String[] { String.valueOf(id) });
    }


	
	public long updateAlarm(AlarmModel model) {
		ContentValues values = populateContent(model);
        return getWritableDatabase().update(Alarm.TABLE_NAME, values, Alarm._ID + " = ?", new String[] { String.valueOf(model.id) });
	}
	
	public AlarmModel getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
		//query to get an alarm based on id
        String query = "select * from " + Alarm.TABLE_NAME + " where " + Alarm._ID + " = " + id;
		Cursor c = db.rawQuery(query, null);
		if (c.moveToNext()) {
			return populateModel(c);
		}
        else {
            return null;
        }
	}
	
	public List<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "select * from " + Alarm.TABLE_NAME;
		Cursor cursor = db.rawQuery(select, null);
		List<AlarmModel> alarms = new ArrayList<>();
		
		while (cursor.moveToNext()) {
			alarms.add(populateModel(cursor));
		}
		if (!alarms.isEmpty()) {
			return alarms;
		}
        else{
            return null;
        }
	}
}
