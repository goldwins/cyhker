package com.cycrypt.http.query;

public interface IHttpRequester {

	public String stringResultForURL(String urlStr);
	
	public void setProxy(String ip,String port);
}
