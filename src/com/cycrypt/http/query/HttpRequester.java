package com.cycrypt.http.query;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequester implements IHttpRequester {


	public String stringResultForURL(String urlStr) {
		if (urlStr == null || urlStr.length() == 0)
			return null;
		URL url=null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return this.readHTMLContent3(url);
	}

	private String readHTMLContent3(URL url) {
		StringBuffer result = new StringBuffer();
		HttpURLConnection connection = null;
		BufferedReader reader =null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();

			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				result.append(lines);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if(reader!=null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection!=null)
				connection.disconnect();
		}
		return result.toString();
	}

	public static void main(String[] args) {
		String url = "http://cyworld.ifensi.com/services/sso/check_user.php?param=e9f1db661d83552ac25afe0328074ec3dce954f850f07140ed015eee52229c19b9721cd35593a86782c3f7e90d4a6b66b1e662753fdfbf5904bc2db2d30cf78117c41db18f7bfd1c224a9f7b5558cc82c716f80d6ac15515e9266cbf5883c749f00f6ba857";
		String a = new HttpRequester().stringResultForURL(url);
		System.out.println(a);
	}

	@Override
	public void setProxy(String ip, String port) {
		// TODO Auto-generated method stub
		System.setProperty("http.proxyHost",ip);
		System.setProperty("http.proxyPort", port);
	}
}
