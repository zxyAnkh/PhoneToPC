package com.example.phonetopc.control;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

public class ConnectManager {
	private static final String TAG = "ConnectManager";
	private static final int TIME_OUT = 10*1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
	private Context mContext;
	public static String URL_FIRST = "http://";
	public static String URL_LAST =":8080/PhoneToPC/"; //连接地址
	public ConnectManager(Context mContext){
		this.mContext = mContext;
	}
	public Boolean isConnect(String id){
		try {
			URL url = new URL(URL_FIRST+id+URL_LAST);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
