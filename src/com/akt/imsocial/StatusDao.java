package com.akt.imsocial;

import winterwell.jtwitter.Twitter.Status;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StatusDao {
	private static final String TAG = "Social";
	public static final String DB_NAME="social.db";
	public static final int DB_VERSION = 3;
	public static final String DB_TABLE_NAME = "statuses";
	public static final String C_ID = "_id";
	public static final String C_STATUS = "status_text";
	public static final String C_CREATED_AT = "created_at";
	public static final String C_NAME = "name";

	Context context;
	SQLiteDatabase myDb;
	DbHelper dbHelper;
	
	public StatusDao(Context context){
		this.context = context;
		dbHelper = new DbHelper(context);
	}
	
	public void insert(Status status){
		ContentValues values = new ContentValues();
		values.put(C_ID, status.getId());
		values.put(C_NAME, status.getUser().getName());
		values.put(C_STATUS, status.getText());
		values.put(C_CREATED_AT, status.getCreatedAt().toString());
		
		myDb = dbHelper.getWritableDatabase();
		myDb.insertWithOnConflict(DB_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
	}
	
	public Cursor query(){
		myDb = dbHelper.getReadableDatabase();
		Cursor c = myDb.query(DB_TABLE_NAME, null, null, null, null, null, C_ID + " DESC");
		return c;
	}
	
	
	class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DB_NAME,null,DB_VERSION);
			Log.d(TAG, "Constructor Initialized");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		String sql  = String.format("create table %s (%s int primary key, %s text, %s text, %s int)", DB_TABLE_NAME, C_ID, C_NAME, C_STATUS, C_CREATED_AT);
		Log.d(TAG, "DB Created: "+sql);
		db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = "drop table "+DB_TABLE_NAME;
			if(newVersion>oldVersion){
				Log.d(TAG, "DB Updated: "+sql);
				db.execSQL(sql);
				onCreate(db);
			}
		}
		
	}

}
