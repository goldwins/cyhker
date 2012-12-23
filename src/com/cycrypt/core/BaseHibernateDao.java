package com.cycrypt.core;

import net.sf.ehcache.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class BaseHibernateDao {
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected Session getCurrentSession() {
		 return sessionFactory.getCurrentSession();
	}
	
	protected Session getNewSession()
	{
		return sessionFactory.openSession();
	}
}
