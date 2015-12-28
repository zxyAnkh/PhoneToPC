package com.example.phonetopc.control;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetDetect {
	private Context mContext;
	public NetDetect(Context context){
		this.mContext = context;
	}
	// �ж������Ƿ���ͨ
		public Boolean isNetWork() {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (mConnectivityManager.getActiveNetworkInfo() != null) {
				return mConnectivityManager.getActiveNetworkInfo().isAvailable();
			}
			return false;
		}

		// �ж�Wifi״��
		public boolean isWifiConnected() {
			if (mContext != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
						.getSystemService(Context.CONNECTIVITY_SERVICE); // ��ȡϵͳ�����ӷ���
				NetworkInfo mWiFiNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // ��ȡ������������
				if (mWiFiNetworkInfo != null) {
					return mWiFiNetworkInfo.isAvailable();
				}
			}
			return false;
		}

		// �ж�GPRS״��
		public boolean isGPRSConnected() {
			if (mContext != null) {
				ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
						.getSystemService(Context.CONNECTIVITY_SERVICE); // ��ȡϵͳ�����ӷ���
				NetworkInfo mGPRSNetWorkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // ��ȡ������������
				if (mGPRSNetWorkInfo != null) {
					return mGPRSNetWorkInfo.isAvailable();
				}
			}
			return false;
		}
}
