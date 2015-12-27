package com.example.phonetopc.view;

import java.util.ArrayList;

import com.example.phonetopc.R;
import com.example.phonetopc.control.FileList;
import com.example.phonetopc.model.FileDownInfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class UploadActivity extends Activity{
	private TextView txt_filepath;
	private TextView txt_filename;
	private ListView lv_upfile;
	private Button btn_upload;
	private FileList fr;
	private ArrayAdapter<FileDownInfo> mAdatper;
	private ArrayList<FileDownInfo> mFileNameList = new ArrayList<FileDownInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		init();
	}
	void init(){
		txt_filepath = (TextView)findViewById(R.id.txt_filepath);
		txt_filename = (TextView)findViewById(R.id.txt_filename);
		lv_upfile = (ListView)findViewById(R.id.lv_upfile);
		btn_upload = (Button)findViewById(R.id.btn_upload);
	}
}
