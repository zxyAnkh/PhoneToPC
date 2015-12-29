package com.example.phonetopc.model;

public class FileLoadInfo {
	private String file_name;
	private String file_type;
	private double file_size;

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

	public double getFile_size() {
		return file_size;
	}

	public void setFile_size(double file_size) {
		this.file_size = file_size;
	}

	public FileLoadInfo() {
		this.file_name = "";
		this.file_size = 0;
		this.file_type = "";
	}

	public FileLoadInfo(String arg0, String arg1, double arg2) {
		this.file_name = arg0;
		this.file_type = arg1;
		this.file_size = arg2;
	}
}
