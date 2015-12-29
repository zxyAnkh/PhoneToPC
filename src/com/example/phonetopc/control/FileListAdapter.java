package com.example.phonetopc.control;

import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.R;
import com.example.phonetopc.model.FileDownInfo;
import com.example.phonetopc.model.FileLoadInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileListAdapter extends ArrayAdapter<String> {
	private static String TAG = "FIleListAdapter";
	private ArrayList<String> mArrayList;
	private Context mContext;

	public FileListAdapter(Context context, int resource,
			ArrayList<String> fileList) {
		super(context, 0, fileList);
		this.mContext = context;
		this.mArrayList = fileList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public String getItem(int position) {
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
		txt_lv_file.setText(mArrayList.get(position).toString());
		return convertView;

	}
}
