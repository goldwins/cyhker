package com.cycrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.cycrypt.log.Loger;
import com.cycrypt.utils.FilePathUtil;

public class Config {
	
	public static final String RUN_MODE_TYPE_LOCAL_MACHINE = "LOCAL_MACHINE";
	public static final String RUN_MODE_TYPE_SAE = "SAE";
	public static final String PERSIST_MODE_TYPE_DB="DB";
	public static final String PERSIST_MODE_TYPE_FILE="FILE";
	
	private String runMode;
	private String dbURL;
	private String dbUserName;
	private String dbPass;
	private String dbDriverClass;
	private int threadCount;
	private String persistMode;
	private String cyIdRangeMask;
	
	private String dbURLWrite;
	private String dbURLRead;
	


	public String getDbURLWrite() {
		return dbURLWrite;
	}

	public String getDbURLRead() {
		return dbURLRead;
	}

	public String getCyIdRangeMask() {
		return cyIdRangeMask;
	}

	public String getPersistMode() {
		return persistMode;
	}

	public int getThreadCount() {
		return threadCount;
	}

	private static Config instance = null;
	
	private Config()
	{
		if(!initFromPropertiesFile())
			throw new RuntimeException("Faild init Config");
	}
	
	public static Config getInstance()
	{
		if(instance==null)
			instance = new Config();
		return instance;
	}
	
	public void reload()
	{
		instance =null;
		instance = new Config();
	}
	
/*	private void setDefaultValues()
	{
		this.runMode = RUN_MODE_TYPE_LOCAL_MACHINE;
	}*/
	
	private boolean initFromPropertiesFile()
	{
		Properties prop = new Properties();
		String fileName = "Config.properties";
		InputStream in =null;
		boolean flag = false;
		try {
			
			boolean outFileExist = false;
			String outerFilePath =null; 
			File outerConfigFile = null;
			try{
				outerFilePath=FilePathUtil.getRunningPath()+File.separatorChar+fileName;
				outerConfigFile = new File (outerFilePath);
				outFileExist = outerConfigFile.exists();
			}catch(Exception e)
			{
				outFileExist = false;
			}
			
			if(outFileExist)
			{
				Loger.debug(outerFilePath);
				in=new FileInputStream(outerFilePath);
			}else
			{
				//Loger.debug(File.separatorChar+fileName);
				in=getClass().getResourceAsStream("/"+fileName);
				
			}
			
			prop.load(in);
			this.runMode = prop.getProperty("RUN_MODE",RUN_MODE_TYPE_LOCAL_MACHINE);
			if(this.runMode==null)
			{
				Loger.error("RUN_MODE IS EMPTY IN CONFIG FILE.");
			}else
			{
				this.dbURL = prop.getProperty("DB_URL");
				this.dbUserName = prop.getProperty("DB_USER_NAME");
				this.dbPass = prop.getProperty("DB_PASS");
				this.dbDriverClass = prop.getProperty("DB_DRIVER_CLASS","com.mysql.jdbc.Driver");
				this.threadCount = Integer.parseInt(prop.getProperty("THREAD_COUNT","10"));
				this.persistMode = prop.getProperty("PERSIST_MODE");
				this.cyIdRangeMask = prop.getProperty("CYID_RANGE_MASK");
				this.dbURLWrite = prop.getProperty("DB_URL_WRITE");
				this.dbURLRead = prop.getProperty("DB_URL_READ");
				flag = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}finally
		{
			try {
				if(in!=null)
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return flag;
	}
	
	public String getRunMode() {
		return runMode;
	}
	public String getDbURL() {
		return dbURL;
	}
	public String getDbUserName() {
		return dbUserName;
	}
	public String getDbPass() {
		return dbPass;
	}
	
	public String getDbDriverClass() {
		return dbDriverClass;
	}
	
	
}
