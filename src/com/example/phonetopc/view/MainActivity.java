package com.example.phonetopc.view;

import java.util.Timer;
import java.util.TimerTask;

import com.example.phonetopc.R;
import com.example.phonetopc.control.ConnectManager;
import com.example.phonetopc.control.NetDetect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	private EditText edt_dns;
	private EditText edt_username;
	private EditText edt_password;
	private TextView txt_tips;
	private Button btn_cchat;
	private Button btn_login;
	private Button btn_quit;
	private Button btn_csend;
	private Button btn_creceive;
	private String strDNS;
	private String strUsername;
	private String strPassword;
	public static boolean isLogin;
	private Handler mHandler;
	private ConnectManager cm;
	private NetDetect nd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	// 初始化操作
	public void init() {
		isLogin = true;
		edt_dns = (EditText) findViewById(R.id.edt_dns);
		edt_dns.setText("zxyankh.imwork.net");
		edt_username = (EditText) findViewById(R.id.edt_username);
		edt_username.setText("zxy");
		edt_password = (EditText) findViewById(R.id.edt_password);
		edt_password.setText("zxy");
		txt_tips = (TextView) findViewById(R.id.txt_tips);
		btn_quit = (Button) findViewById(R.id.btn_quit);
		btn_csend = (Button) findViewById(R.id.btn_choosesend);
		btn_cchat = (Button) findViewById(R.id.btn_choosechat);
		btn_creceive = (Button) findViewById(R.id.btn_choosereceive);
		btn_login = (Button) findViewById(R.id.btn_login);
		nd = new NetDetect(this);
		if (!nd.isNetWork()) {
			Toast.makeText(this, "网络连接异常，请检查网络", Toast.LENGTH_SHORT).show();
			btn_login.setEnabled(false);
		} else if (nd.isWifiConnected()) {
			Toast.makeText(this, "现在是Wifi环境下", Toast.LENGTH_SHORT).show();
			btn_login.setEnabled(true);
		} else if (nd.isGPRSConnected()) {
			dialogGPRS();
		}
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strDNS = edt_dns.getText().toString();
				strUsername = edt_username.getText().toString();
				strPassword = edt_password.getText().toString();
				Log.d(TAG, strDNS + strUsername + strPassword);
				if (strDNS.equals("")) {
					Toast.makeText(MainActivity.this, "请输入域名地址",
							Toast.LENGTH_SHORT).show();
				} else if (strUsername.equals("")) {
					Toast.makeText(MainActivity.this, "请输入用户名",
							Toast.LENGTH_SHORT).show();
				} else if (strPassword.equals("")) {
					Toast.makeText(MainActivity.this, "请输入密码",
							Toast.LENGTH_SHORT).show();
				} else {
					mHandler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (msg.what == 0x123) {
								if (msg.obj.toString().equals("Login")) {
									isLogin = true;
								} else {
									isLogin = false;
									Toast.makeText(MainActivity.this,
											msg.obj.toString(),
											Toast.LENGTH_SHORT).show();
								}
							}
						}
					};
					cm = new ConnectManager(mHandler, MainActivity.this,
							"log"+strUsername+strPassword);
					new Thread(cm).start();
					if (isLogin == true) {
						Log.d(TAG, "Connect Success");
						txt_tips.setVisibility(View.VISIBLE);
						edt_dns.setEnabled(false);
						edt_username.setEnabled(false);
						edt_password.setEnabled(false);
						btn_csend.setVisibility(View.VISIBLE);
						btn_creceive.setVisibility(View.VISIBLE);
						btn_cchat.setVisibility(View.VISIBLE);
						btn_login.setVisibility(View.INVISIBLE);
						btn_quit.setVisibility(View.VISIBLE);
					} 
				}
			}
		});
		btn_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cm = new ConnectManager(mHandler, MainActivity.this,
						"qui"+strUsername+strPassword);
				new Thread(cm).start();
				edt_dns.setEnabled(true);
				edt_username.setEnabled(true);
				edt_password.setEnabled(true);
				txt_tips.setVisibility(View.INVISIBLE);
				btn_csend.setVisibility(View.INVISIBLE);
				btn_creceive.setVisibility(View.INVISIBLE);
				btn_cchat.setVisibility(View.INVISIBLE);
				btn_login.setVisibility(View.VISIBLE);
				btn_quit.setVisibility(View.INVISIBLE);
				isLogin = false;
			}
		});
		btn_csend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, UploadActivity.class);
				startActivity(i);
			}
		});
		btn_creceive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, DownloadActivity.class);
				startActivity(i);
			}
		});
		btn_cchat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ChatActivity.class);
				startActivity(i);
			}
		});
	}

	// 当不是wifi环境下时询问是否继续使用流量
	// 参考 http://blog.csdn.net/liang5630/article/details/44098899
	public void dialogGPRS() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		DialogInterface.OnClickListener dialogGPRSOnClicListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case Dialog.BUTTON_POSITIVE:
					Toast.makeText(MainActivity.this, "使用流量继续",
							Toast.LENGTH_SHORT).show();
					btn_login.setEnabled(true);
					break;
				case Dialog.BUTTON_NEGATIVE:
					Toast.makeText(MainActivity.this, "请连接Wifi",
							Toast.LENGTH_SHORT).show();
					btn_login.setEnabled(false);
					break;
				}
			}
		};
		builder.setTitle("提示");
		builder.setMessage("当前不处于Wifi网络，继续操作需要消耗流量，是否继续？");
		builder.setPositiveButton("继续", dialogGPRSOnClicListener);
		builder.setNegativeButton("取消", dialogGPRSOnClicListener);
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
