package com.cycrypt.mission;

import org.apache.log4j.Logger;

import com.cycrypt.Config;

public class CrackerMissionController {
	
	private int runningThreadCount=0;
	private DoneCallBack allThreadDoneCallBack;
	private boolean isNowCreatingThread;
	private MHIDMaker idMaker;
	private int threadCountToRun;
	
	public static boolean runningFlag=false;
	
	Logger log = Logger.getLogger(this.getClass());
	
	public CrackerMissionController(MHIDMaker idMaker,DoneCallBack callback)
	{
		threadCountToRun =Config.getInstance().getThreadCount();
		allThreadDoneCallBack = callback;
		isNowCreatingThread = false;
		this.idMaker = idMaker;
		log.debug("Initialied without threadCount");
	}
	
	public CrackerMissionController(MHIDMaker idMaker,DoneCallBack callback,int threadCount)
	{
		threadCountToRun =threadCount;
		allThreadDoneCallBack = callback;
		isNowCreatingThread = false;
		this.idMaker = idMaker;
		log.debug("Initialied with threadCount:"+threadCount);
		
	}
	
	public synchronized void increaseThreadCount()
	{
		runningThreadCount++;
		if(runningThreadCount==0)
		{
			if(!isNowCreatingThread)
				this.allThreadDoneCallBack.done();
		}
	}
	
	public synchronized void oneThreadDone()
	{
		runningThreadCount--;
		if(runningThreadCount==0)
		{
			if(!isNowCreatingThread)
				this.allThreadDoneCallBack.done();
		}
			
	}
	
	public void runMissions()
	{
		//int threadCount = Config.getInstance().getThreadCount();
		log.debug("start to runMissions,with threadCount:"+threadCountToRun);
		runningFlag =true;
		isNowCreatingThread = true;
		for(int i=0;i<threadCountToRun;i++)
		{
			
			new Thread(new CrackerThread(idMaker,new DoneCallBack(){

				@Override
				public void done() {
					// TODO Auto-generated method stub
					oneThreadDone();
				}})).start();
			 increaseThreadCount();
		}
		isNowCreatingThread = false;
		log.debug("finish to runMissions,with threadCount:"+threadCountToRun+", now running thread:"+getRunningThreadCount());
	}
	
	public int getRunningThreadCount()
	{
		return runningThreadCount;
	}
	
	public String getCurrentCYID()
	{
		return this.idMaker.getCurrentCYID();
	}
	
}
