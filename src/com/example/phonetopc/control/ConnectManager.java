package com.example.phonetopc.control;

/*参考网站：
 * http://blog.csdn.net/chenzheng_java/article/details/6387116
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.example.phonetopc.model.PasswordTransfer;
import com.example.phonetopc.view.MainActivity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

//网络连接管理
public class ConnectManager implements Runnable {
	private static final String TAG = "ConnectManager";
	private static final int TIME_OUT = 10 * 1000; // 超时时间
	private String message;
	private Context mContext;
	private Handler mHandler;
	public Handler revHandler;
	private String URL = "zxyankh.imwork.net";
	boolean isLogin = false;
	BufferedReader br;
	PrintWriter pw;
	OutputStream os = null;

	// 构造器
	public ConnectManager(Handler handler, Context context, String msg) {
		this.mHandler = handler;
		this.mContext = context;
		this.message = msg;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut,"UTF-8"), true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn,"UTF-8"));
	}

	public void run() {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(URL, 5648), TIME_OUT);
			br = getReader(socket);
			pw = getWriter(socket);
			String outMsg = PasswordTransfer.wordToPwd(message);
			Log.d(TAG, outMsg);
			pw.println("log"+outMsg);
			//由于使用bufferedWriter时忘记写br.nextLine() 导致通信阻塞，寻找问题用了3天，写在这里，仅以此为戒
			//2015-12-23 15:49
			pw.flush();
			String inMsg = null;
			while ((inMsg = br.readLine()) != null) {
				if (inMsg.equals("Login")) {
					Message msg = new Message();
					msg.what = 0x123;
					msg.obj = inMsg;
					mHandler.sendMessage(msg);
				}else if(inMsg.equals("用户名或密码错误")){
					Message msg = new Message();
					msg.what = 0x123;
					msg.obj = inMsg;
					mHandler.sendMessage(msg);
				}
			}
			pw.close();
			br.close();
			socket.close();
		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "网络连接超时！";
			mHandler.sendMessage(msg);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}
