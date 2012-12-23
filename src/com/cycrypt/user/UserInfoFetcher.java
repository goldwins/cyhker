package com.cycrypt.user;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UserInfoFetcher {
	
	
	public static void main(String[] args) throws IOException
	{
		Document doc = Jsoup.connect("http://cyworld.ifensi.com/ps2/profile/basic_info.php?mh_id=2001729706").get();
		Elements els = doc.select("a.blue[href='#']");
		//Elements els2 = els.select("href=#");
		Iterator<Element> i =els.iterator();
		while(i.hasNext())
		{
			Element e = i.next();
			System.out.println(e.data());
		}
	}
}
