package com.example.phonetopc.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.model.ChatInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChatManager {
	private Context mContext;
	private DBHelper mHelper;
	private SQLiteDatabase dbR;
	private SQLiteDatabase dbW;
	private String tblName = "BeanChat";
	public ChatManager(Context context){
		this.mContext = context;
		this.mHelper = new DBHelper(mContext,null);
		this.dbR = mHelper.getReadableDatabase();
		this.dbW = mHelper.getWritableDatabase();
	}
	public List<ChatInfo> loadChatHistory(){
		List<ChatInfo> chatInfo = new ArrayList<ChatInfo>();
		ChatInfo ci = new ChatInfo();
		String[] columns = new String[] { "chat_word","chat_time","chat_wtw"};
		String[] str = new String[3];
		Cursor cursor = dbR.query(tblName, columns, null, null,
				null, null, null);
		while (cursor.moveToNext()) {
			for (int i = 0; i < str.length; i++) {
				str[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
			}
			ci = new ChatInfo(str[0], str[1],str[2]);
			chatInfo.add(ci);
		}
		cursor.close();
		return chatInfo;
		
	}
	public void addChat(ChatInfo ci){
		if (ci == null)
			return;
		try {
			ContentValues values = new ContentValues();
			values.put("chat_word", ci.getChat_word());
			values.put("chat_time", ci.getChat_time());
			values.put("chat_wtw", ci.getChat_wtw());
			dbW.insert(tblName, null, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
