/* 
 * Pregnancy Calculator
 * PregCalcData.java
 * Database helper activity which interfaces with the local SQLite database
 * This class is referenced from the other classes
 *  
 * Dylan Perales
 * Version 1.1
 */

package com.android.pregnancycalculator;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.provider.BaseColumns;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class PregCalcData extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "pregcalc.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_NAME = "preginfo";

	public static final String _ID = BaseColumns._ID;
	public static final String BABY_NAME = "babyName";
	public static final String DUE_DATE = "due_date";
	public static final String WEEKS_ALONG = "weeks_along";
	public static final String DAYS_ALONG = "days_along";
	public static final String WEEKS_TOGO = "weeks_togo";
	public static final String DAYS_TOGO = "days_togo";

	public PregCalcData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public PregCalcData(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// Method to create TABLE
	@Override
	public void onCreate(SQLiteDatabase db) {
//		String sql = "CREATE TABLE " + TABLE_NAME + " (" + _ID
//				+ " INTEGER PRIMARY KEY, " + BABY_NAME + " TEXT NOT NULL, "
//				+ DUE_DATE + " TEXT NOT NULL, " + WEEKS_ALONG + " INTEGER"
//				+ DAYS_ALONG + " INTEGER" + WEEKS_TOGO + " INTEGER" + DAYS_TOGO
//				+ " INTEGER" + ");";
		
		String sql = 
			"CREATE TABLE " + TABLE_NAME + " (" + BABY_NAME + " TEXT NOT NULL, "
			+ DUE_DATE + " TEXT NOT NULL, " + WEEKS_ALONG + " INTEGER"
			+ DAYS_ALONG + " INTEGER" + WEEKS_TOGO + " INTEGER" + DAYS_TOGO
			+ " INTEGER" + ");";

		db.execSQL(sql);
	}

	// Method to upgrade database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Upgrade database (Drop old and create new)
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	// Used for inserting values into Table
	public void insert(String babyName, String dueDate, int weeksAlong,
			int daysAlong, int weeksTogo, int daysTogo) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(BABY_NAME, babyName);
		values.put(DUE_DATE, dueDate);
		values.put(WEEKS_ALONG, weeksAlong);
		values.put(DAYS_ALONG, daysAlong);
		values.put(WEEKS_TOGO, weeksTogo);
		values.put(DAYS_TOGO, daysTogo);

		db.insertOrThrow(TABLE_NAME, null, values);
	}

	// Only used when returning more than 1 record (I only use 1)
	 public Cursor all(Activity activity){
	 String[] from = { _ID, BABY_NAME, DUE_DATE, WEEKS_ALONG };
	 String order = BABY_NAME;
	
	 SQLiteDatabase db = getReadableDatabase();
	 Cursor cursor = db.query(TABLE_NAME, from, null, null, null, null,
	 order);
	 activity.startManagingCursor(cursor);
	
	 return cursor;
	 }

	// Returns number of entries in database (Always 1 or 0)
	public long count() {
		SQLiteDatabase db = getReadableDatabase();
		return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
	}

}
