package com.example.phonetopc.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.example.phonetopc.model.FileUpInfo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class FileUploader implements Runnable {
	private static final String TAG = "FileUploader";
	private Context mContext;
	private Handler mHandler;
	public static Handler revHandler;
	private FileUpInfo fui;
	private BufferedReader br;
	private PrintWriter pw;
	private DataOutputStream dos;
	private FileInputStream fis;

	public FileUploader(Handler handler, Context context, FileUpInfo fui) {
		this.mContext = context;
		this.mHandler = handler;
		this.fui = fui;
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn, "UTF-8"));
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut, "UTF-8"));
	}

	private FileInputStream getFIS(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	private DataOutputStream getDOS(Socket socket) throws IOException {
		return new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("zxyankh.imwork.net", 5648),
					5000);
			br = getReader(socket);
			pw = getWriter(socket);
			dos = getDOS(socket);
			final String name = fui.getFile_name();
			final String type = fui.getFile_type();
			final String path = fui.getFile_path();
			final double size = fui.getUp_size();
			final double size1 = fui.getFile_size();
			new Thread() {
				@Override
				public void run() {
					try {
						fis = getFIS(new File(path, name + "." + type));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pw.println("upp" + name + "." + type + "." + size1);
					pw.flush();
					String inMsg = null;
					try {
						while ((inMsg = br.readLine()) != null) {
							if (inMsg.equals("upload")) {
								Log.d(TAG, inMsg);
								byte[] bt = new byte[1024];
								int length;
								double sumL = 0;
								while ((length = fis.read(bt, 0, bt.length)) > 0) {
									sumL += length;
									dos.write(bt, 0, length);
									dos.flush();
									Message msg = new Message();
									msg.what = 0x123;
									msg.obj = (int) ((sumL / size1) * 100);
									mHandler.sendMessage(msg);
								}
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			Looper.prepare();
			revHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 0x345) {
						pw.println("upc" + msg.obj.toString());
						pw.flush();
					}
				}
			};
			Looper.loop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
