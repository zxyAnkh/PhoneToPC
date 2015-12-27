package com.example.phonetopc.control;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class FileDownloader implements Runnable {
	private static final String TAG = "FileDownloader";
	private Handler mHandler;
	public static Handler revHandler;
	private BufferedReader br;
	private PrintWriter pw;
	private String filename;
	private String filetype;
	private FileOutputStream fos;
	private DataInputStream dis;
	private int filesize;
	private static final String FILE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/FileDatabase";

	public FileDownloader(Handler handler, String name, String type, int size) {
		this.mHandler = handler;
		this.filename = name;
		this.filesize = size;
		this.filetype = type;
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn, "UTF-8"));
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut, "UTF-8"), true);
	}

	private DataInputStream getDIS(Socket socket) throws IOException {
		return new DataInputStream(socket.getInputStream());
	}

	private FileOutputStream getFOS(File file) throws FileNotFoundException {
		return new FileOutputStream(file);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("zxyankh.imwork.net", 5648));
			pw = getWriter(socket);
			dis = getDIS(socket);
			File file = new File(FILE_PATH, filename + "." + filetype);
			fos = getFOS(file);
			byte[] inputByte = new byte[1024];
			int length = 0;
			double sumL = 0;
			pw.println("dow" + filename + "." + filetype);
			pw.flush();
			while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
				Log.d(TAG, "¿ªÊ¼ÏÂÔØ");
				fos.write(inputByte, 0, length);
				sumL += length;
				fos.flush();
				if (sumL == length) {

				}
			}
			Looper.prepare();
			revHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 0x123) {

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
