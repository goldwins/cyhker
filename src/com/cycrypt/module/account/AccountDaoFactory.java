package com.cycrypt.module.account;

import com.cycrypt.Config;

public class AccountDaoFactory {
	
	private static AccountDaoFactory instance;
	
	private AccountDaoFactory()
	{

	}
	
	public static synchronized AccountDaoFactory getInstance()
	{
		if(instance==null)
			instance = new AccountDaoFactory();
		return instance;
	}
	
	private Config cfg = Config.getInstance();
	public IAccountDao getAccountDao()
	{
		if(cfg.getRunMode().equals(Config.RUN_MODE_TYPE_SAE))
			return new AccountDaoJDBCMySQL();
		if(cfg.getPersistMode().equals(Config.PERSIST_MODE_TYPE_DB))
			return new AccountDao();
		
		return new AccountDaoFile();
		
	}
}
