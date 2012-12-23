package com.cycrypt.module.account;


public class AccountDaoFile implements IAccountDao {

	private DataPool dp = DataPool.getInstance();
	

	public boolean saveAccount(Account account) {
		// TODO Auto-generated method stub
		return dp.addData(account.getCyId()+"\t"+account.getEmail()+"\t"+account.getPass());
	}

	@Override
	public Integer getLastCyId() {
		// TODO Auto-generated method stub
		String lastLine = dp.getLastLine();
		if(lastLine==null)
			return null;
		String[] datas = lastLine.split("\t");
		if(datas==null||datas.length<1)
			return null;
		Integer result = null;
		try
		{
			result = Integer.parseInt(datas[0]);
		}catch (NumberFormatException ne) {
			// TODO: handle exception
			result = null;
		}
		return result;
	}



}
