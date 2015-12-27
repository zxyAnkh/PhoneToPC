package com.example.phonetopc.control;

import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.model.FileDownInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FileDownManager {
	private static final String TAG = "FileDownManager";
	private Context mContext;
	private DBHelper mHelper;
	private SQLiteDatabase dbR;
	private SQLiteDatabase dbW;
	private static final String TBL_NAME = "BeanDown";

	public FileDownManager(Context context) {
		this.mContext = context;
		this.mHelper = new DBHelper(mContext, null);
		this.dbR = mHelper.getReadableDatabase();
		this.dbW = mHelper.getWritableDatabase();
	}

	// 读取所有未完成的下载任务
	public List<FileDownInfo> loadAllDownloading() {
		List<FileDownInfo> downList = new ArrayList<FileDownInfo>();
		String[] columns = new String[] { "file_name", "file_type",
				"down_size", "file_size" };
		String where = "down_size != ?";
		String[] str = new String[2];
		int size, size1;// down_size,file_size
		String[] wherevalue = new String[] { "file_size" };
		Cursor cursor = dbR.query(TBL_NAME, columns, where, wherevalue, null,
				null, null);
		while (!cursor.moveToNext()) {
			for (int i = 0; i < str.length; i++) {
				str[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
			}
			size = cursor.getInt(cursor.getColumnIndex(columns[3]));
			size1 = cursor.getInt(cursor.getColumnIndex(columns[4]));
			FileDownInfo fdi = new FileDownInfo(str[0], str[1], size, size1);
			downList.add(fdi);
		}
		cursor.close();
		Log.d(TAG, downList.get(0).getFile_name());
		return downList;

	}

	// 添加下载
	public void addDownload(FileDownInfo fdi) {
		if (fdi == null)
			return;
		ContentValues value = new ContentValues();
		value.put("file_name", fdi.getFile_name());
		value.put("file_type", fdi.getFile_type());
		value.put("down_size", fdi.getDown_size());
		value.put("file_size", fdi.getFile_size());
		dbW.insert(TBL_NAME, null, value);
		Log.d(TAG, "add success");
	}

	// 更新下载进度
	public void updateDownload(FileDownInfo fdi) {
		if (fdi == null)
			return;
		ContentValues value = new ContentValues();
		value.put("down_size", fdi.getDown_size());
		dbW.update(TBL_NAME, value, "file_name",
				new String[] { String.valueOf(fdi.getDown_size()) });
	}
}
