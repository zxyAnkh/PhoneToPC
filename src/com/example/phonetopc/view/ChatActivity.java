package com.example.phonetopc.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.phonetopc.R;
import com.example.phonetopc.control.ChatListAdapter;
import com.example.phonetopc.control.ChatManager;
import com.example.phonetopc.control.ChatThread;
import com.example.phonetopc.model.ChatInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 参考自：http://blog.csdn.net/x605940745/article/details/17001641
 */

public class ChatActivity extends Activity {
	private static final String TAG = "ChatActivity";

	private ListView show;
	private EditText input;
	private Button send;
	private String message;
	private Handler mHandler;
	private ChatListAdapter mAdapter;
	private ChatManager cm;
	private ChatThread chatThread;

	List<ChatInfo> mChatInfo = new ArrayList<ChatInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		init();

	}

	public void init() {
		input = (EditText) findViewById(R.id.edt_msg);
		show = (ListView) findViewById(R.id.lv_chat);
		send = (Button) findViewById(R.id.btn_send);

		cm = new ChatManager(this);
		mChatInfo = cm.loadChatHistory();
		showHistory(mChatInfo);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				add(msg.obj.toString());
				reloadList();
			}
		};
		chatThread = new ChatThread(mHandler);
		new Thread(chatThread).start();
		// 添加聊天信息至listview
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				message = input.getText().toString();
				if (!message.equals("")) {
					ChatInfo ci = new ChatInfo();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					ci.setChat_time(sdf.format(System.currentTimeMillis()));
					ci.setChat_word(message);
					ci.setChat_wtw("utoa");
					cm.addChat(ci);
					// 发送信息至chatThread
					Message msg = new Message();
					msg.what = 0x345;
					msg.obj = message;
					chatThread.revHandler.sendMessage(msg);
					input.setText("");
					reloadList();
				}
			}
		});
	}

	private void reloadList() {
		mChatInfo = cm.loadChatHistory();
		showHistory(mChatInfo);
	}
	
	private void add(String msg){
		ChatInfo ci = new ChatInfo();
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		ci.setChat_time(sdf.format(System.currentTimeMillis()));
		ci.setChat_word(msg);
		Log.d(TAG, msg);
		ci.setChat_wtw("atou");
		cm.addChat(ci);
	}

	private void showHistory(List<ChatInfo> cList) {
		ArrayList<ChatInfo> aList = new ArrayList<ChatInfo>();
		for (int i = 0; i < cList.size(); i++) {
			aList.add(cList.get(i));
		}
		mAdapter = new ChatListAdapter(this, aList);
		show.setAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
