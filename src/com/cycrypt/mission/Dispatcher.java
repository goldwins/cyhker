package com.cycrypt.mission;


public class Dispatcher {
	private MHIDMaker idMaker;
	
	public Dispatcher(MHIDMaker idMaker)
	{
		this.idMaker = idMaker;
	}
	
	public synchronized String getId()
	{
		return this.idMaker.getNextMHID();
	}
	
	
	
	
}
