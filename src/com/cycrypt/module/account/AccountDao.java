package com.cycrypt.module.account;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.cycrypt.Config;
import com.cycrypt.core.BaseHibernateDao;

public class AccountDao extends BaseHibernateDao implements IAccountDao{

	Logger log = Logger.getLogger(this.getClass());
	@Override
	public boolean saveAccount(Account account) {
		// TODO Auto-generated method stub
		try
		{
			log.debug("getting session");
			Session s = this.getNewSession();
			//Session s = this.getCurrentSession();
			log.debug("session sessionGetted");
			s.beginTransaction();
			log.debug("transaction beggined");
			log.debug("saving data.."+account.toString());
			s.save(account);
			//s.flush();
			log.debug("finished saving data..");
			s.getTransaction().commit();
			log.debug("transaction commited");
			s.close();
			//log.debug("session closed.");
		}catch(Exception e)
		{
			log.debug("Exception:"+e.getMessage());
			log.debug(e);
			log.debug(e.getClass());
			StackTraceElement[] ele = e.getStackTrace();
			for(StackTraceElement el:ele)
			{
				log.debug(el.getClassName()+"."+el.getMethodName()+":"+el.getLineNumber());
			}
			e.printStackTrace();
			
		}
		
		return true;
	}

	@Override
	public Integer getLastCyId() {
		// TODO Auto-generated method stub
		//Session s = this.getCurrentSession();
		Session s = this.getNewSession();
		String hql = "select max(t.cyId) from Account t";
		Config cfg = Config.getInstance();
		String rangeMask = cfg.getCyIdRangeMask();
		String baseCyIdIfNull = null;
		if(rangeMask!=null&&rangeMask.trim().length()>0)
		{
			hql+=" where t.cyId like '"+rangeMask+"%'";
			baseCyIdIfNull = rangeMask+"000000";
		}
		s.createQuery(hql);
		Query q = s.createQuery(hql);
		String result =(String)q.uniqueResult();
		s.close();
		if(result!=null&&!"".equals(result))
			return Integer.valueOf(result);
		return Integer.valueOf(baseCyIdIfNull)-1;
	}

	
	
}
