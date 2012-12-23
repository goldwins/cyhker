package com.cycrypt.cfg;

import com.cycrypt.DicMaker;

public abstract class CryptConfig {
	
	public CryptConfig(String cyId)
	{
		this.cyId = cyId;
	}
	
	private String cyId;

	public void setCyId(String cyId) {
		this.cyId = cyId;
	}

	public String getCyId() {
		return cyId;
	}

	public abstract int getMaxContetLen();
	
	
	public abstract String getContentIndexQuery(String l, int index, int contentLen) ;

	public abstract String getContentInstrQuery(String l) ;

	public abstract String getContentLenQuery(int len) ;
	
	public String[] getBaseDic()
	{
		return DicMaker.BASE_LETTER_DIC;
	}
	
	protected String getContentLikeString(String l,int index,int contentLen)
	{
		StringBuffer sb = new StringBuffer(contentLen);
		for(int i=0;i<contentLen;i++)
		{
			sb.append(i==index?l:"_");			
		}
		return sb.toString();
	}
	
	
	
	
	protected String getContentLikeString(String l) {
		// TODO Auto-generated method stub
		return "%"+l+"%";
	}
}
