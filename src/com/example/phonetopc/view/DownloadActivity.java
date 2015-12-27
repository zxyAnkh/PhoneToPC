package com.example.phonetopc.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;

import com.example.phonetopc.R;
import com.example.phonetopc.control.ChatListAdapter;
import com.example.phonetopc.control.FileDownListAdapter;
import com.example.phonetopc.control.FileDownManager;
import com.example.phonetopc.control.FileDownloader;
import com.example.phonetopc.control.FileList;
import com.example.phonetopc.control.FileLoader;
import com.example.phonetopc.model.ChatInfo;
import com.example.phonetopc.model.FileDownInfo;
import com.example.phonetopc.model.FileLoadInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadActivity extends Activity {
	private static final String TAG = "DownloadActivity";
	private FileList mFileList;
	private Button btn_downloading;
	private ListView lv_down;
	private Handler mHandler;
	private FileLoader fl;
	private FileDownManager fdm;
	private FileDownListAdapter mAdapter;
	private static final String FILE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/FileDatabase";
	//以为是在sd卡中建立文件夹，结果建在了内部存储中 12-28 19:48
	ArrayList<FileLoadInfo> fileList = new ArrayList<FileLoadInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		init();
	}

	private void init() {
		createDirectory();
		lv_down = (ListView) findViewById(R.id.lv_down);
		btn_downloading = (Button) findViewById(R.id.btn_downloading);
		mFileList = new FileList(this);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					String info = msg.obj.toString();
					FileLoadInfo fli = new FileLoadInfo();
					fli.setFile_name(info.substring(0, info.indexOf(".")));
					fli.setFile_type(info.substring(info.indexOf(".") + 1,
							info.lastIndexOf(".")));
					fli.setFile_size(Integer.parseInt(info.substring(info
							.lastIndexOf(".") + 1)));
					reloadList(fli);
					// Log.d(TAG, msg.obj.toString());
				}
			}
		};
		fl = new FileLoader(mHandler);
		new Thread(fl).start();
		lv_down.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FileLoadInfo file = (FileLoadInfo) parent.getAdapter().getItem(
						position);
				dialogSure(file.getFile_name(), file.getFile_type(),
						file.getFile_size());
			}

		});
		btn_downloading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(DownloadActivity.this,
						DownloadingActivity.class);
				startActivity(i);
			}
		});
	}

	private void createDirectory() {
		// TODO Auto-generated method stub
		String strdir = FILE_PATH;
		File dir = new File(strdir);
		if (!dir.exists()){
			dir.mkdir();
			Log.d(TAG, "创建文件夹");
		}
	}

	private void dialogSure(final String arg0, final String arg1, final int arg2) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		DialogInterface.OnClickListener dialogOnClicListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case Dialog.BUTTON_POSITIVE:
					Toast.makeText(DownloadActivity.this, "加入下载队列",
							Toast.LENGTH_SHORT).show();
					// 开始下载 发出下载请求
					startDownload(arg0, arg1, arg2);
					break;
				case Dialog.BUTTON_NEGATIVE:
					break;
				}
			}
		};
		builder.setTitle("提示");
		builder.setMessage("文件名:" + arg0 + "文件类型:" + arg1 + "文件大小:" + arg2
				+ "\n" + "确定下载？");
		builder.setPositiveButton("下载", dialogOnClicListener);
		builder.setNegativeButton("取消", dialogOnClicListener);
		builder.create().show();
	}

	private void reloadList(FileLoadInfo fli) {
		fileList.add(fli);
		mAdapter = new FileDownListAdapter(this, fileList);
		lv_down.setAdapter(mAdapter);
	}

	// arg0 文件名 arg1 文件类型 arg2 文件大小
	private void startDownload(String arg0, String arg1, int arg2) {
		fdm = new FileDownManager(this);
		FileDownInfo fdi = new FileDownInfo(arg0, arg1, 0, arg2);
		fdm.addDownload(fdi);
//		Log.d(TAG, arg0+arg1+arg2);
	}
}
