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

import com.example.phonetopc.model.FileDownInfo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class FileDownloader implements Runnable {
	private static final String TAG = "FileDownloader";
	private Handler mHandler;
	private Context mContext;
	public static Handler revHandler;
	private BufferedReader br;
	private PrintWriter pw;
	private String filename;
	private String filetype;
	private FileOutputStream fos;
	private DataInputStream dis;
	private boolean bl = true;// true正在下载 false不在下载
	private double filesize;
	private double downsize;
	private static final String FILE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/FileDatabase";

	public FileDownloader(Context context, Handler handler, String name,
			String type, double size, double size1) {
		this.mHandler = handler;
		this.filename = name;
		this.filesize = size1;
		this.filetype = type;
		this.downsize = size;
		this.mContext = context;
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
			final FileDownManager fdm = new FileDownManager(mContext);
			FileDownInfo fdi = new FileDownInfo(filename, filetype, downsize,
					filesize);
			fos = getFOS(file);
			byte[] inputByte = new byte[1024];
			int length = 0;
			double sumL = 0;
			if (bl == true) {
				pw.println("dow" + filename + "." + filetype + "." + downsize);
				pw.flush();
				while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
					Log.d(TAG, "开始下载");
					fos.write(inputByte, 0, length);
					sumL += length;
					fos.flush();
					downsize = sumL;
					if (sumL == length)
						fdm.deleteDownload(fdi);
					Message msg = new Message();
					msg.what = 0x345;
					msg.obj = (int) ((sumL / filesize) * 100);
					Log.d(TAG,
							String.valueOf(sumL) + "   "
									+ String.valueOf(filesize));
					mHandler.sendMessage(msg);
				}
			} else if (bl == false) {
				String msg = null;
				while ((msg = br.readLine()) != null) {

				}
			}
			Looper.prepare();// 获取是否暂停的信息
			revHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 0x123) {
						if (msg.obj.equals("true")) {// 继续下载
							bl = true;
						} else if (msg.obj.equals("false")) {// 暂停下载
							bl = false;
							pw.println("sto" + filename + "." + filetype + "."
									+ downsize);
							pw.flush();
							FileDownInfo fdi = new FileDownInfo(filename,
									filetype, downsize, filesize);
							fdm.updateDownload(fdi);
						}
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
