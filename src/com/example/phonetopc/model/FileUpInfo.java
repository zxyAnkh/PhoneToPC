package com.example.phonetopc.model;

public class FileUpInfo {
	private String file_name;
	private String file_type;
	private double up_size;
	private double file_size;
	private String file_path;
	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

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

	public double getUp_size() {
		return up_size;
	}

	public void setUp_size(double up_size) {
		this.up_size = up_size;
	}

	public FileUpInfo() {
		this.up_size = 0;
		this.file_name = "";
		this.file_type = "";
		this.file_size = 0;
		this.file_path="";
	}

	public FileUpInfo(String name, String type, String path,double size, double size1) {
		this.up_size = size;
		this.file_name = name;
		this.file_type = type;
		this.file_size = size1;
		this.file_path = path;
	}
	public FileUpInfo(String name,String type,String path,double size){
		this.file_name = name;
		this.file_size = size;
		this.up_size = 0;
		this.file_type = type;
		this.file_path = path;
	}
}
