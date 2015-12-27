package com.example.phonetopc.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class FileListItem extends LinearLayout{
	private static Context mContext;
	public FileListItem(Context context){
		super(context);
		mContext = context;
	}
	public FileListItem(Context context, AttributeSet attrs){
		super(context, attrs);
		mContext = context;
	}
	
}
