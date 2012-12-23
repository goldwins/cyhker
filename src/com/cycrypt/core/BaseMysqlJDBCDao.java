package com.cycrypt.core;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cycrypt.Config;

public abstract class BaseMysqlJDBCDao {
	private Connection writeConn;
	private Connection readConn;
	private static Logger log = Logger.getLogger("BaseMysqlJDBCDao");
	private static String dbUrl;
	private static String dbUrlWrite;
	private static String dbUrlRead;
	private static String dbUserName;
	private static String dbPass;
	private static String driver;
	
	private final int CONNECTION_TYPE_WRITE = 0;
	private final int CONNECTION_TYPE_READ = 1;
	
	static
	{
		Config cfg =  Config.getInstance();
		dbUrl = cfg.getDbURL();
		dbUrlWrite = cfg.getDbURLWrite();
		if(dbUrlWrite==null)
		{
			dbUrlWrite = dbUrl;
			log.warn("no dbUrlWrite value found use default.");
		}
		dbUrlRead = cfg.getDbURLRead();
		if(dbUrlRead==null)
		{
			dbUrlRead = dbUrl;
			log.warn("no dbUrlRead value found use default.");
		}
		dbUserName = cfg.getDbUserName();
		dbPass = cfg.getDbPass();
		driver = cfg.getDbDriverClass();
	}
	
	private boolean isConnectionCanUse(Connection c) throws SQLException
	{
		return c!=null&&c.isClosed()&&c.isValid(500);
	}
	
	private void processInvalidConnection(Connection c)
	{
		if(c!=null)
		{
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
	private Connection getConnectionByType(int type) throws SQLException
	{
		Connection targetConn = type==CONNECTION_TYPE_READ?readConn:writeConn;
			if(!this.isConnectionCanUse(targetConn))
			{
				this.processInvalidConnection(targetConn);
				targetConn=null;
			}
		
			targetConn = this.getNewConnection(type==CONNECTION_TYPE_READ?dbUrlRead:dbUrlWrite);
		
		return targetConn;
	}
	
	protected Connection getWriteConnection() throws SQLException
	{
		return this.getConnectionByType(CONNECTION_TYPE_WRITE);
	}
	
	protected Connection getReadConnection() throws SQLException
	{
		return this.getConnectionByType(CONNECTION_TYPE_READ);
	}
	
	private Connection getNewConnection(String url)
	{
		return this.getNewConnection(driver, url, dbUserName, dbPass);
	}

	private Connection getNewConnection(String driver,String url,String userName,String pass)
	{
		java.sql.Connection c = null;
		try {
			Class.forName(driver);
			c = java.sql.DriverManager.getConnection(url, userName, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		} 
		return c;
	}
	
	private Connection openNewWriteConnection()
	{
		return this.getNewConnection(driver,dbUrlWrite, dbUrl,  dbPass);
	}
	
	private Connection openReadConnection()
	{
		return this.getNewConnection(driver,dbUrlRead, dbUrl,  dbPass);
	}
}
