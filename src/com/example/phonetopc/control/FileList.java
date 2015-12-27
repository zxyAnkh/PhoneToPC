package com.example.phonetopc.control;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;


public class FileList {
	private Context mContext;
	@SuppressLint("NewApi") 
	private String filePath = android.os.Environment.getRootDirectory().getAbsolutePath();
	public List<File> fileList;
	public FileList(Context context){
		this.mContext = context;
	}
	
}
