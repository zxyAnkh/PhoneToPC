package com.example.phonetopc.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.R;
import com.example.phonetopc.control.FileListAdapter;
import com.example.phonetopc.control.FileUpManager;
import com.example.phonetopc.model.FileDownInfo;
import com.example.phonetopc.model.FileUpInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends Activity {
	// btn_uploading txt_filepath lv_upfile
	private static final String TAG = "UploadActivity";
	private Button btn_uploading;
	private TextView txt_filepath;
	private ListView lv_upfile;
	private String path;
	private String rootpath = "/system";
	private String sdpath = "/sdcard";
	boolean is = Environment.getExternalStorageState().equals(
			Environment.MEDIA_MOUNTED);// 判断是否装载sd卡 结果发现不是手机的外置SD卡
	private ArrayAdapter<String> mAdatper;
	private ArrayList<String> fileList;
	private File file;
	private FileUpManager fum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		init();
	}

	void init() {
		btn_uploading = (Button) findViewById(R.id.btn_uploading);
		txt_filepath = (TextView) findViewById(R.id.txt_filepath);
		lv_upfile = (ListView) findViewById(R.id.lv_upfile);
		fum = new FileUpManager(this);
		txt_filepath.setText("本地");
		btn_uploading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(UploadActivity.this,
						UploadingActivity.class);
				startActivity(i);
			}

		});
		fileList = new ArrayList<String>();
		fileList.add("内部存储");
		if (is)
			fileList.add("SD卡");
		loadFile();
		lv_upfile.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String str = (String) parent.getAdapter().getItem(position);
				if (str.equals("内部存储")) {
					IntoRoot();
				} else if (str.equals("SD卡")) {
					IntoSD();
				} else if (str.equals("返回上一层")) {
					back();
				} else if (str.substring(0, 5).equals("文件夹名:")) {
					IntoFile(str);
				} else if (str.substring(0, 4).equals("文件名:")) {
					dialogSure(str);
				}
			}
		});
	}

	private void IntoRoot() {
		txt_filepath.setText(rootpath);
		file = new File(rootpath);
		File[] files = file.listFiles();
		fileList.removeAll(fileList);
		fileList.add("返回上一层");
		if (files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory() && f.canRead())
					fileList.add("文件夹名:" + f.getName() + "文件大小:" + f.length());
				else if (f.isFile() && f.canRead())
					fileList.add("文件名:" + f.getName() + "文件大小:" + f.length());
				// Log.d(TAG, f.getName());
			}
		}
		loadFile();
	}

	private void IntoSD() {
		txt_filepath.setText(sdpath);
		file = new File(sdpath);
		File[] files = file.listFiles();
		fileList.removeAll(fileList);
		fileList.add("返回上一层");
		if (files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory() && f.canRead())
					fileList.add("文件夹名:" + f.getName() + "文件大小:" + f.length());
				else if (f.isFile() && f.canRead())
					fileList.add("文件名:" + f.getName() + "文件大小:" + f.length());
				// Log.d(TAG, f.getName());
			}
		}
		loadFile();
	}

	private void back() {
		file = new File(txt_filepath.getText().toString());
		File f = new File(file.getParentFile().getAbsoluteFile().toString());
		if (file.getParentFile().getAbsoluteFile().toString().equals("/")) {
			txt_filepath.setText("本地");
			fileList.removeAll(fileList);
			fileList.add("内部存储");
			fileList.add("SD卡");
			loadFile();
		} else {
			txt_filepath.setText(file.getParentFile().getAbsoluteFile()
					.toString());
			File[] files = f.listFiles();
			fileList.removeAll(fileList);
			fileList.add("返回上一层");
			if (files.length != 0) {
				for (int i = 0; i < files.length; i++) {
					File f1 = files[i];
					if (f1.isDirectory() && f1.canRead())
						fileList.add("文件夹名:" + f1.getName() + "文件大小:"
								+ f1.length());
					else if (f1.isFile() && f1.canRead())
						fileList.add("文件名:" + f1.getName() + "文件大小:"
								+ f1.length());
					// Log.d(TAG, f.getName());
				}
			}
			loadFile();
		}
	}

	private void IntoFile(String str) {
		// Log.d(TAG, str.substring(0, 5));
		// Log.d(TAG, str.substring(5, str.lastIndexOf("文")));
		String filename = str.substring(5, str.lastIndexOf("文"));
		path = txt_filepath.getText().toString();
		txt_filepath.setText(path + "/" + filename);
		file = new File(txt_filepath.getText().toString());
		// Log.d(TAG, txt_filepath.getText().toString());
		File[] files = file.listFiles();
		fileList.removeAll(fileList);
		fileList.add("返回上一层");
		if (files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				File f1 = files[i];
				if (f1.isDirectory() && f1.canRead())
					fileList.add("文件夹名:" + f1.getName() + "文件大小:" + f1.length());
				else if (f1.isFile() && f1.canRead())
					fileList.add("文件名:" + f1.getName() + "文件大小:" + f1.length());
				// Log.d(TAG, f.getName());
			}
		}
		loadFile();
	}

	private void dialogSure(final String arg0) {
		final String path = txt_filepath.getText().toString();
		Log.d(TAG, path);
		if (arg0.indexOf(".") > 0) {
			final String name = arg0.substring(4, arg0.indexOf("."));
			final String type = arg0.substring(arg0.indexOf(".") + 1,
					arg0.lastIndexOf("文"));
			final double size = Double.valueOf(arg0.substring(arg0
					.lastIndexOf(":") + 1));
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			DialogInterface.OnClickListener dialogOnClicListener = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case Dialog.BUTTON_POSITIVE:
						Toast.makeText(UploadActivity.this, "加入上传队列",
								Toast.LENGTH_SHORT).show();
						// 开始上传
						fum.addUpload(new FileUpInfo(name, type, path, 0, size));
						break;
					case Dialog.BUTTON_NEGATIVE:
						break;
					}
				}
			};
			builder.setTitle("提示");
			builder.setMessage(arg0 + "\n" + "确定上传？");
			builder.setPositiveButton("上传", dialogOnClicListener);
			builder.setNegativeButton("取消", dialogOnClicListener);
			builder.create().show();
		}
	}

	private void loadFile() {
		mAdatper = new FileListAdapter(this, 0, fileList);
		lv_upfile.setAdapter(mAdatper);
	}
	// 返回键实现返回上一层
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	//
	// if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	// back();
	// }
	// return super.onKeyDown(keyCode, event);
	// }
}
