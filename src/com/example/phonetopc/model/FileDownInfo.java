package com.example.phonetopc.model;

public class FileDownInfo {
	private String file_name;
	private String file_type;
	private double down_size;
	private double file_size;

	public double getFile_size() {
		return file_size;
	}

	public void setFile_size(double file_size) {
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

	public double getDown_size() {
		return down_size;
	}

	public void setDown_size(double down_size) {
		this.down_size = down_size;
	}

	public FileDownInfo() {
		this.down_size = 0;
		this.file_name = "";
		this.file_type = "";
		this.file_size = 0;
	}

	public FileDownInfo(String name, String type, double size, double size1) {
		this.down_size = size;
		this.file_name = name;
		this.file_type = type;
		this.file_size = size1;
	}
	public FileDownInfo(String name,String type,double size){
		this.file_name = name;
		this.file_size = size;
		this.down_size = 0;
		this.file_type = type;
	}
}
