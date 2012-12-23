package com.cycrypt.module.account;

public interface IAccountDao {
	public boolean saveAccount(Account account);
	public Integer getLastCyId();
}
