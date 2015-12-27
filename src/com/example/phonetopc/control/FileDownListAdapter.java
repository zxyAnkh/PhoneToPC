package com.example.phonetopc.control;

import java.util.ArrayList;

import com.example.phonetopc.model.ChatInfo;
import com.example.phonetopc.model.FileLoadInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileDownListAdapter extends ArrayAdapter<FileLoadInfo> {
	private Context mContext;
	private ArrayList<FileLoadInfo> mArrayList;

	public FileDownListAdapter(Context context, ArrayList<FileLoadInfo> aList) {
		super(context, 0, aList);
		this.mContext = context;
		this.mArrayList = aList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public FileLoadInfo getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int itemId) {
		// TODO Auto-generated method stub
		return itemId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TextView(mContext);
		}
		TextView txt_lv_file = (TextView) convertView;
		txt_lv_file.setText("文件名:" + mArrayList.get(position).getFile_name()
				+ "      文件类型:" + mArrayList.get(position).getFile_type()
				+ "      文件大小:" + mArrayList.get(position).getFile_size());
		return convertView;

	}
}
