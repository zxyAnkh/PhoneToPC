package com.example.phonetopc.model;

public class FileDownInfo {
	private String file_name;
	private String file_type;
	private int down_size;
	private int file_size;

	public int getFile_size() {
		return file_size;
	}

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public int getDown_size() {
		return down_size;
	}

	public void setDown_size(int down_size) {
		this.down_size = down_size;
	}

	public FileDownInfo() {
		this.down_size = 0;
		this.file_name = "";
		this.file_type = "";
		this.file_size = 0;
	}

	public FileDownInfo(String name, String type, int size, int size1) {
		this.down_size = size;
		this.file_name = name;
		this.file_type = type;
		this.file_size = size1;
	}
}
