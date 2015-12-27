package com.example.phonetopc.control;

import java.util.List;

import com.example.phonetopc.R;
import com.example.phonetopc.model.FileDownInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FileListAdapter extends ArrayAdapter<FileDownInfo> {
	private static String TAG = "FIleListAdapter";
	private LayoutInflater mInflater;

	public FileListAdapter(Context context, int resource, List<FileDownInfo> objects) {
		super(context, resource, objects);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = mInflater.inflate(R.layout.txt_lv_file, parent, false);
		}

		FileListItem listItem = (FileListItem) view;

		return view;
	}
}
