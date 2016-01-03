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

	// ��ȡ����δ��ɵ���������
	public List<FileDownInfo> loadAllDownloading() {
		List<FileDownInfo> downList = new ArrayList<FileDownInfo>();
		String[] columns = new String[] { "file_name", "file_type",
				"down_size", "file_size" };
		String where = "down_size < ?";
		String[] str = new String[2];
		int size, size1;// down_size,file_size
		String[] wherevalue = new String[] { "file_size" };
		Cursor cursor = dbR.query(TBL_NAME, columns, where, wherevalue, null,
				null, null);
		// db.query("����",new String[]{"�ֶ�1","�ֶ�2"},"����1"=? and "����2"=?
		// ,new String[]{"����1��ֵ,����2��ֵ"},groupby,having,orderby);
		while (cursor.moveToNext()) {
			for (int i = 0; i < str.length; i++) {
				str[i] = cursor.getString(cursor.getColumnIndex(columns[i]));
			}
			size = cursor.getInt(cursor.getColumnIndex(columns[2]));
			size1 = cursor.getInt(cursor.getColumnIndex(columns[3]));
			FileDownInfo fdi = new FileDownInfo(str[0], str[1], size, size1);
			Log.d(TAG, fdi.getFile_name());
			downList.add(fdi);
		}
		cursor.close();
		return downList;

	}

	// �������
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

	// �������ؽ���
	public void updateDownload(FileDownInfo fdi) {
		if (fdi == null)
			return;
		ContentValues value = new ContentValues();
		value.put("down_size", fdi.getDown_size());
		dbW.update(TBL_NAME, value, "file_name = ?",
				new String[] { fdi.getFile_name() });
	}

	public void deleteDownload(FileDownInfo fdi) {
		if (fdi == null)
			return;
		String where = "file_name = ? and file_type = ? and file_size = ?";
		String[] wherevalue = new String[] { fdi.getFile_name(),
				fdi.getFile_type(), String.valueOf(fdi.getFile_size()) };
		dbW.delete(TBL_NAME, where, wherevalue);
	}
}
