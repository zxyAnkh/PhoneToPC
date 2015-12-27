package com.example.phonetopc.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.example.phonetopc.model.FileLoadInfo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FileLoader implements Runnable {
	private static final String TAG = "FileLoader";
	private Handler mHandler;
	private BufferedReader br;
	private PrintWriter pw;

	public FileLoader(Handler handler) {
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
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("zxyankh.imwork.net", 5648),
					5 * 1000);
			br = getReader(socket);
			pw = getWriter(socket);
			String outMsg = "loa";
			pw.println(outMsg);
			pw.flush();
			Log.d(TAG, outMsg);
			String inMsg = null;
			while ((inMsg = br.readLine()) != null) {
				Message msg = new Message();
				msg.what = 0x123;
				msg.obj = inMsg;
				Log.d(TAG, inMsg);
				mHandler.sendMessage(msg);
			}
		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "ÍøÂçÁ¬½Ó³¬Ê±£¡";
			mHandler.sendMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null) {
				pw.close();
				try {
					br.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
