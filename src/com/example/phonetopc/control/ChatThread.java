package com.example.phonetopc.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;

import com.example.phonetopc.model.ChatInfo;
import com.example.phonetopc.view.ChatActivity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ChatThread implements Runnable {
	private static final String TAG = "ChatThread";

	private Handler mHandler;
	public Handler revHandler;
	private Socket mSocket;
	private PrintWriter pw;
	private BufferedReader br;

	public ChatThread(Handler handler) {
		// TODO Auto-generated constructor stub
		this.mHandler = handler;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut, "UTF-8"), true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn, "UTF-8"));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		mSocket = new Socket();
		try {
			mSocket.connect(new InetSocketAddress("zxyankh.imwork.net", 5648),
					10 * 1000);
			br = getReader(mSocket);
			pw = getWriter(mSocket);
			new Thread() {
				// 不开线程则停止运行
				// 无法将消息发送至主线程
				public void run() {
					String inMsg = null;
					try {
						while ((inMsg = br.readLine()) != null) {
							Message msg = Message.obtain();
							msg.obj = inMsg;
							mHandler.sendMessage(msg);
							Log.d(TAG, msg.obj.toString());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			Looper.prepare();
			// 创建revHandler对象
			revHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					// 接收到UI线程中用户输入的数据
					if (msg.what == 0x345) {
						// 将用户在文本框输入的内容写入网络
						try {
							pw.println("cha" + msg.obj.toString());
							pw.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
			// 启动Looper
			Looper.loop();
		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "网络连接超时！";
			mHandler.sendMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pw.close();
				br.close();
				mSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
