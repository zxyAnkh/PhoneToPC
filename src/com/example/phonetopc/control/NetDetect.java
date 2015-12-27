package com.example.phonetopc.control;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetDetect {
	private Context mContext;
	public NetDetect(Context context){
		this.mContext = context;
	}
	// 判断网络是否连通
		public Boolean isNetWork() {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (mConnectivityManager.getActiveNetworkInfo() != null) {
				return mConnectivityManager.getActiveNetworkInfo().isAvailable();
			}
			return false;
		}

		// 判断Wifi状况
		public boolean isWifiConnected() {
			if (mContext != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
						.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取系统的连接服务
				NetworkInfo mWiFiNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // 获取网络的连接情况
				if (mWiFiNetworkInfo != null) {
					return mWiFiNetworkInfo.isAvailable();
				}
			}
			return false;
		}

		// 判断GPRS状况
		public boolean isGPRSConnected() {
			if (mContext != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
						.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取系统的连接服务
				NetworkInfo mGPRSNetWorkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // 获取网络的连接情况
				if (mGPRSNetWorkInfo != null) {
					return mGPRSNetWorkInfo.isAvailable();
				}
			}
			return false;
		}
}
