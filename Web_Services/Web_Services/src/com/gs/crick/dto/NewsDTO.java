package com.gs.crick.dto;

import java.util.Date;

public class NewsDTO {
	

	
	private 	String  newsid;
	private 	String newsName;
	private 	String newsDesc;
	
	
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getNewsName() {
		return newsName;
	}
	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}
	public String getNewsDesc() {
		return newsDesc;
	}
	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}
	
}
