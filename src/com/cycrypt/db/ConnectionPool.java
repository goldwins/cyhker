package com.cycrypt.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.cycrypt.Config;


public class ConnectionPool {
	
	/*private static String host="localhost";
	private static String port="3306";
	private static String userName="root";
	private static String passWord = "goldwins";
	private static String database = "cyworld";*/
	
	private static DataSource ds;
	
	private static void init()
	{
		if(ds!=null)
			return;
		Config cfg = Config.getInstance();
		String connURI =cfg.getDbURL();
		BasicDataSource bds = new BasicDataSource();
		bds.setUrl(connURI);
		bds.setDriverClassName(cfg.getDbDriverClass());
		bds.setUsername(cfg.getDbUserName()); 
		bds.setPassword(cfg.getDbPass());
		bds.setInitialSize(5); 
		bds.setMaxActive(10);
		ds = bds;
	}
	
	public static Connection getConnection() throws SQLException
	{
		if(ds==null)
			init();
		return ds.getConnection();
	}

}
