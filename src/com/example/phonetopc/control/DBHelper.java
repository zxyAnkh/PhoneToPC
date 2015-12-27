package com.example.phonetopc.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private Context mContext;
	private static final String DB_NAME = "PTP";
	private static int version = 1;

	String sqlChat = "create table BeanChat(_id integer primary key autoincrement,chat_word text, chat_time text,chat_wtw text)";
	String sqlDownload = "create table BeanDown(_id integer primary key autoincrement,file_name text,file_type text,down_size integer,file_size integer)";

	public DBHelper(Context context, CursorFactory factory) {
		super(context, DB_NAME, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlChat);
		db.execSQL(sqlDownload);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
