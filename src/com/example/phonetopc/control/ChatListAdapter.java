package com.example.phonetopc.control;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.phonetopc.model.ChatInfo;
import com.example.phonetopc.view.ChatActivity;

public class ChatListAdapter extends ArrayAdapter<ChatInfo>{
	private Context mContext;
	private ArrayList<ChatInfo> mChatInfo;
	public ChatListAdapter(Context context, ArrayList<ChatInfo> aList) {
		super(context,0,aList);
		this.mContext = context;
		this.mChatInfo = aList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mChatInfo.size();
	}

	@Override
	public ChatInfo getItem(int position) {
		// TODO Auto-generated method stub
		return mChatInfo.get(position);
	}

	@Override
	public long getItemId(int itemId) {
		// TODO Auto-generated method stub
		return itemId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TextView(mContext);
		}
		TextView txt_lv_chat_content = (TextView) convertView;
		if(mChatInfo.get(position).getChat_wtw().equals("atou"))
			txt_lv_chat_content.setText("对方 "+mChatInfo.get(position).getChat_time()+"说：");
		else
			txt_lv_chat_content.setText("我 "+mChatInfo.get(position).getChat_time()+"说：");
		txt_lv_chat_content.append("\n"+mChatInfo.get(position).getChat_word());
		return convertView;

	}
}
