package com.example.phonetopc.view;

import com.example.phonetopc.R;
import com.example.phonetopc.control.ConnectManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	private EditText edt_pcip;
	private TextView txt_tips;
	private Button btn_connect;
	private Button btn_csend;
	private Button btn_creceive;
	private String strip;
	private ConnectManager cm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cm = new ConnectManager(this);
		edt_pcip = (EditText) findViewById(R.id.edt_pcip);
		txt_tips = (TextView) findViewById(R.id.txt_tips);
		btn_csend = (Button) findViewById(R.id.btn_choosesend);
		btn_creceive = (Button) findViewById(R.id.btn_choosereceive);
		btn_connect = (Button) findViewById(R.id.btn_connect);
		btn_connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strip = edt_pcip.getText().toString();
				if (cm.isConnect(strip)) {
					Log.d(TAG, "Connect Success");
					txt_tips.setVisibility(View.VISIBLE);
					btn_csend.setVisibility(View.VISIBLE);
					btn_creceive.setVisibility(View.VISIBLE);
				} else {
					Log.d(TAG, "Connect Falied");
					Toast.makeText(MainActivity.this, "¡¨Ω” ß∞‹",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		btn_csend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, SendActivity.class);
				startActivity(i);
			}
		});
		btn_creceive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ReceiveActivity.class);
				startActivity(i);
			}
		});
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
