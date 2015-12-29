package com.example.phonetopc.view;

import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.R;
import com.example.phonetopc.control.FileUpManager;
import com.example.phonetopc.control.FileUploader;
import com.example.phonetopc.model.FileUpInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UploadingActivity extends Activity {
	private static final String TAG = "UploadingActivity";
	private LayoutInflater mInflater;
	private LinearLayout rootLayout;
	private ImageButton ibtn_ups;
	private ImageButton ibtn_upc;
	private TextView txt_uploading;
	private ProgressBar prg_uploading;
	private FileUpManager fum;
	private Handler mHandler;
	private List<FileUpInfo> fuList = new ArrayList<FileUpInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploading);
		mInflater = LayoutInflater.from(this);
		rootLayout = (LinearLayout) findViewById(R.id.rootlayout_upload);
		init();
	}

	private void init() {
		fum = new FileUpManager(this);
		fuList = fum.loadAllUploading();
		for (int i = 0; i < fuList.size(); i++) {
			doUpload(fuList.get(i));
		}
	}

	private void doUpload(FileUpInfo fui) {
		createView(fui);
		FileUploader ful = new FileUploader(mHandler, this, fui);
		new Thread(ful).start();
	}

	private void createView(final FileUpInfo fui) {
		final String name = fui.getFile_name();
		final String type = fui.getFile_type();
		double size = fui.getUp_size();
		final double size1 = fui.getFile_size();
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		final LinearLayout linearLayout = (LinearLayout) inflater.inflate(
				R.layout.uploading, null);
		txt_uploading = (TextView) linearLayout.getChildAt(0);
		txt_uploading.setText(name);
		prg_uploading = (ProgressBar) linearLayout.getChildAt(1);
		prg_uploading.setProgress((int) (size / size1 * 100));
		ibtn_ups = (ImageButton) linearLayout.getChildAt(2);
		ibtn_upc = (ImageButton) linearLayout.getChildAt(3);
		ibtn_ups.setEnabled(false);// 暂未实现暂停、继续功能
		ibtn_upc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fum.deleteUpload(fui);
				rootLayout.removeView(linearLayout);
				Message msg = new Message();
				msg.what = 0x345;
				msg.obj = name + "." + type + "." + size1;
				FileUploader.revHandler.sendMessage(msg);
			}
		});
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					int progress = 0;
					progress = Integer.parseInt(msg.obj.toString());
					prg_uploading.setProgress(progress);
					if(progress == 100){
						fui.setUp_size(size1);
						fum.updateUpload(fui);
					}
					Log.d(TAG, String.valueOf(progress));
				}
			}
		};
		rootLayout.addView(linearLayout);
	}
}
