package com.cycrypt.http.query;

import com.sina.sae.fetchurl.SaeFetchurl;

public class HttpRequesterFetchURL implements IHttpRequester {
	private SaeFetchurl fetchUrl = new SaeFetchurl();

	@Override
	public String stringResultForURL(String urlStr) {
		String result = null;
		try
		{
			result = fetchUrl.fetch(urlStr);
			if(result==null)
				throw new HttpQueryException("Fetch url returned null.");
		}catch(Exception e)
		{
			throw new HttpQueryException(e.getMessage());
		}
		return  result;
	}

	@Override
	public void setProxy(String ip, String port) {
		// TODO Auto-generated method stub
		System.setProperty("http.proxyHost",ip);
		System.setProperty("http.proxyPort", port);
	}
	


}
