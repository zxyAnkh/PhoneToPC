package com.example.phonetopc.control;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.phonetopc.model.FileUpInfo;

public class FileUpManager {
	private static final String TAG = "FileUpManager";
	private Context mContext;
	private DBHelper mHelper;
	private SQLiteDatabase dbR;
	private SQLiteDatabase dbW;
	private static final String TBL_NAME = "BeanUp";

	public FileUpManager(Context context) {
		this.mContext = context;
		this.mHelper = new DBHelper(mContext, null);
		this.dbR = mHelper.getReadableDatabase();
		this.dbW = mHelper.getWritableDatabase();
	}

	// ��ȡ����δ��ɵ���������
	public List<FileUpInfo> loadAllUploading() {
		List<FileUpInfo> UpList = new ArrayList<FileUpInfo>();
		String[] columns = new String[] { "file_name", "file_type",
				"file_path", "up_size", "file_size" };
		String where = "up_size < ?";
		String[] str = new String[3];
		int size, size1;// up_size,file_size
		String[] wherevalue = new String[] { "file_size" };
		Cursor cursor = dbR.query(TBL_NAME, columns, where, wherevalue, null,
				null, null);
		// db.query("����",new String[]{"�ֶ�1","�ֶ�2"},"����1"=? and "����2"=?
		// ,new String[]{"����1��ֵ,����2��ֵ"},groupby,having,orderby);
		while (cursor.moveToNext()) {
			for (int i = 0; i < str.length; i++) {
				str[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
			}
			size = cursor.getInt(cursor.getColumnIndex(columns[3]));
			size1 = cursor.getInt(cursor.getColumnIndex(columns[4]));
			FileUpInfo fui = new FileUpInfo(str[0], str[1], str[2], size, size1);
			Log.d(TAG, fui.getFile_name());
			UpList.add(fui);
		}
		cursor.close();
		return UpList;

	}

	// �������
	public void addUpload(FileUpInfo fui) {
		if (fui == null)
			return;
		ContentValues value = new ContentValues();
		value.put("file_name", fui.getFile_name());
		value.put("file_type", fui.getFile_type());
		value.put("file_path", fui.getFile_path());
		value.put("up_size", fui.getUp_size());
		value.put("file_size", fui.getFile_size());
		dbW.insert(TBL_NAME, null, value);
		Log.d(TAG, "add success");
	}

	// �������ؽ���
	public void updateUpload(FileUpInfo fui) {
		if (fui == null)
			return;
		ContentValues value = new ContentValues();
		value.put("up_size", fui.getUp_size());
		dbW.update(TBL_NAME, value, "file_name = ? and file_path = ?",
				new String[] { fui.getFile_name(), fui.getFile_path() });
	}

	public void deleteUpload(FileUpInfo fui) {
		if (fui == null)
			return;
		String where = "file_name = ? and file_type = ? and file_size = ? and file_path = ?";
		String[] wherevalue = new String[] { fui.getFile_name(),
				fui.getFile_type(), String.valueOf(fui.getFile_size()),
				fui.getFile_path() };
		dbW.delete(TBL_NAME, where, wherevalue);
	}
}
