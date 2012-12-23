package com.cycrypt.module.account;

public class AccountManager implements IAccountPersistent  {

	private IAccountDao accountDao = AccountDaoFactory.getInstance().getAccountDao();
	
	@Override
	public boolean insertAccount(String cyId, String email, String pass) {
		// TODO Auto-generated method stub
			Account account = new Account();
			account.setCyId(cyId);
			account.setEmail(email);
			account.setPass(pass);
			return this.saveAccount(account);
		
	}

	@Override
	public Integer getLastCyId() {
		// TODO Auto-generated method stub
		return this.accountDao.getLastCyId();
	}
	
	public boolean saveAccount(Account account)
	{
		return this.accountDao.saveAccount(account);
	}
	
}
