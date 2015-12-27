package com.example.phonetopc.view;

import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.R;
import com.example.phonetopc.control.FileDownManager;
import com.example.phonetopc.control.FileDownloader;
import com.example.phonetopc.model.FileDownInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadingActivity extends Activity {
	private ImageButton ibtnStart;
	private ImageButton ibtnCancle;
	boolean bl = true;// true正在下载 false不在下载
	private ProgressBar prg;
	private TextView txtName;
	private FileDownManager fdm;
	private FileDownloader fdl;
	private Handler mHandler;
	List<FileDownInfo> fdiList = new ArrayList<FileDownInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downloading);
		init();
	}

	private void init() {
		ibtnStart = (ImageButton) findViewById(R.id.ibtn_start);
		ibtnCancle = (ImageButton) findViewById(R.id.ibtn_cancle);
		prg = (ProgressBar) findViewById(R.id.prg_downloading);
		txtName = (TextView) findViewById(R.id.txt_downloading);
		fdm = new FileDownManager(this);
		fdiList = fdm.loadAllDownloading();
		if (fdiList != null) {
//			mHandler = new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					if (msg.what == 0x123) {
//
//					}
//				}
//			};
			String name = fdiList.get(0).getFile_name();
			String type = fdiList.get(0).getFile_type();
			int size = fdiList.get(0).getFile_size();
			fdl = new FileDownloader(mHandler, name, type, size);
			new Thread(fdl).start();
			ibtnStart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (bl == true) {// 停止下载
						ibtnStart.setImageResource(R.drawable.start);
						bl = false;
						Message msg = new Message();
						msg.what = 0x123;
						msg.obj = false;
						FileDownloader.revHandler.sendMessage(msg);
					} else if (bl == false) {// 继续下载
						ibtnStart.setImageResource(R.drawable.pause);
						bl = true;
						Message msg = new Message();
						msg.what = 0x123;
						msg.obj = true;
						FileDownloader.revHandler.sendMessage(msg);
					}
				}
			});
		}
	}
}
