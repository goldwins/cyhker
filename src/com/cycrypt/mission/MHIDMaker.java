package com.cycrypt.mission;

import com.cycrypt.module.account.AccountManager;



public class MHIDMaker {
	
	private final static int CY_ID_BASE_VALUE = 2001000099;
	
	private String currentCYID;
	private int beginId=-1;
	private int currentCYIDInt;
	private int endId =-1;
	
	public MHIDMaker(int beginId,int amount)
	{
		this.beginId = beginId;
		this.endId = beginId+amount;
		currentCYID = String.valueOf(beginId);
		currentCYIDInt = beginId;
	}
	
	public MHIDMaker(int amount)
	{
		Integer lastCyId = new AccountManager().getLastCyId();
		if(lastCyId==null)
			lastCyId = CY_ID_BASE_VALUE;
		this.beginId = lastCyId+1;
		this.endId=beginId+amount;
		currentCYID = String.valueOf(beginId);
		currentCYIDInt = beginId;
	}
	
	public MHIDMaker()
	{
		Integer lastCyId = new AccountManager().getLastCyId();
		if(lastCyId==null)
			lastCyId = CY_ID_BASE_VALUE;
		this.beginId = lastCyId+1;
		this.endId = -1;
		currentCYID = String.valueOf(beginId);
		currentCYIDInt = beginId;
	}
	
	public String getCurrentCYID()
	{
		return currentCYID;
	}
	
	
	public synchronized String getNextMHID()
	{
		String result = currentCYID;
		currentCYIDInt++;
		if(endId>0)
		{
			if(currentCYIDInt>endId)
				return null;				
		}

		currentCYID = String.valueOf(currentCYIDInt);
		return result;
	}
}
