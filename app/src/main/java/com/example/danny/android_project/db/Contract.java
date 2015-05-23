package com.example.danny.android_project.db;

import android.provider.BaseColumns;

public class Contract {
	public static final String DB_NAME = "AlarmClockDatabase.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "tasks";


	public class Columns {
		public static final String TASK = "task";
		public static final String _ID = BaseColumns._ID;
	}
}
