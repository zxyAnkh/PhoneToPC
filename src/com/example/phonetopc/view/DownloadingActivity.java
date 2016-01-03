package com.example.phonetopc.view;

import java.io.File;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadingActivity extends Activity {
	private static final String TAG = "DownloadingActivity";
	private ImageButton ibtnStart;
	private ImageButton ibtnCancel;
	private ProgressBar prg;
	private TextView txtName;
	private LayoutInflater inflater;
	private LinearLayout rootLayout;
	boolean bl = true;// true�������� false��������
	private FileDownManager fdm;
	private static final String FILE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/FileDatabase";
	List<FileDownInfo> fdiList = new ArrayList<FileDownInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_downloading);
		inflater = LayoutInflater.from(this);
		rootLayout = (LinearLayout) findViewById(R.id.rootlayout);
		init();
	}

	// private void refreshView() {
	// rootLayout.invalidate();
	// }

	private void init() {
		fdm = new FileDownManager(this);
		fdiList = fdm.loadAllDownloading();
		for (int i = 0; i < fdiList.size(); i++) {
			download(fdiList.get(i));
		}
	}

	private void download(final FileDownInfo fdi) {
		final String name = fdi.getFile_name();
		final String type = fdi.getFile_type();
		double size1 = fdi.getFile_size();
		double size = fdi.getDown_size();
		createView(name, type, size1, (int) (size / size1 * 100));
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x345) {
					int progress = 0;
					progress = Integer.parseInt(msg.obj.toString());
					prg.setProgress(progress);
					Log.d(TAG, String.valueOf(progress));
					if (progress == 100) {// ���������ɣ�ɾ����Ӧ��view
					// refreshView(); �޷����½��棬�����Ҫ�����������ݿ���Ϣ���ܻᵼ����������
						Toast.makeText(DownloadingActivity.this,
								name + "." + type + "�������", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		};
		FileDownloader fdl = new FileDownloader(this, mHandler, name, type,
				size, size1);
		new Thread(fdl).start();

	}

	// ������ͼ 3�ַ�ʽ 
	// 1.LayoutInflater inflater = getLayoutInflater();
	// ����Activity��getLayoutInflater()
	// 2.LayoutInflater localinflater
	// =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// 3. LayoutInflater inflater = LayoutInflater.from(context);
	private void createView(final String name, final String type,
			final double size1, int progress) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		final LinearLayout linearLayout = (LinearLayout) inflater.inflate(
				R.layout.downloading, null);
		ibtnStart = (ImageButton) linearLayout.getChildAt(2);
		ibtnCancel = (ImageButton) linearLayout.getChildAt(3);
		prg = (ProgressBar) linearLayout.getChildAt(1);
		txtName = (TextView) linearLayout.getChildAt(0);
		txtName.setText(name);
		rootLayout.addView(linearLayout);
		prg.setProgress(progress);
		ibtnStart.setEnabled(false);
		ibtnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bl == true) {// ֹͣ���� ̫����ļ��������᲻�ò��ԣ��ֿ���û��wifi
					ibtnStart.setImageResource(R.drawable.start);
					bl = false;
					Message msg = new Message();
					msg.what = 0x123;
					msg.obj = "false";
					FileDownloader.revHandler.sendMessage(msg);
				} else if (bl == false) {// ��������
					ibtnStart.setImageResource(R.drawable.pause);
					bl = true;
					Message msg = new Message();
					msg.what = 0x123;
					msg.obj = "true";
					FileDownloader.revHandler.sendMessage(msg);
				}
			}
		});
		ibtnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fdm.deleteDownload(new FileDownInfo(name, type, size1));
				File file = new File(FILE_PATH, name + "." + type);
				if (file.isFile() && file.exists()){
					file.delete();
					rootLayout.removeView(linearLayout);
				}
			}
		});
		// while(true){
		// if(prg.getProgress()==100){
		// rootLayout.removeView(linearLayout);
		// }
		// } �ᵼ�³�����
	}
}
