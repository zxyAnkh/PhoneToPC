package com.example.phonetopc.model;

public class ChatInfo {
	private String chat_word;
	private String chat_time;
	private String chat_wtw;

	public String getChat_wtw() {
		return chat_wtw;
	}

	public void setChat_wtw(String chat_wtw) {
		this.chat_wtw = chat_wtw;
	}

	public String getChat_word() {
		return chat_word;
	}

	public void setChat_word(String chat_word) {
		this.chat_word = chat_word;
	}

	public String getChat_time() {
		return chat_time;
	}

	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}

	public ChatInfo(String content, String time,String wtw) {
		this.chat_word = content;
		this.chat_time = time;
		this.chat_wtw=wtw;
	}

	public ChatInfo() {
		this.chat_word = "";
		this.chat_time = "";
		this.chat_wtw="";
	}
}
