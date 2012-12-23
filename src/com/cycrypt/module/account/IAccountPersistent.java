package com.cycrypt.module.account;

public interface IAccountPersistent {
	public boolean insertAccount(String cyId,String email,String pass);
	public Integer getLastCyId();
}
