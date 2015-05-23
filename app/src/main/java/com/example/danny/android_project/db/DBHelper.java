package com.example.danny.android_project.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, Contract.DB_NAME, null, Contract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDB) {
		String sqlQuery =
				String.format("CREATE TABLE %s (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"%s TEXT)", Contract.TABLE_NAME,
								    Contract.Columns.TASK);

		Log.d("TaskDBHelper","Query to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+Contract.TABLE_NAME);
		onCreate(sqlDB);
	}
}
