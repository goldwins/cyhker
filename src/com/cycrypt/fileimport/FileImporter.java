package com.cycrypt.fileimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.cycrypt.module.account.AccountManager;

public class FileImporter {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File f = new File("AFILE2.txt");
		//FileInputStream fis = new FileInputStream(f);
		BufferedReader br = new BufferedReader(new FileReader(f));
		File logFile = new File("FileImportLog.txt");
		FileWriter logWriter = new FileWriter(logFile);
		AccountManager persister = new AccountManager();
		String line = br.readLine();
		while(line!=null)
		{
			String[] data = line.split("\t");
			int len = data.length;
			if(len!=3)
			{
				System.out.println("syntax error: "+line);
				logWriter.append(line);
				logWriter.flush();
			}else
			{
				String cyId = data[0];
				String email = data[1];
				String pass = data[2];
				persister.insertAccount(cyId, email, pass);
				System.out.println(line);
			}
			line = br.readLine();
		}
		System.out.println("All done");
		logWriter.close();
		br.close();
	}
	
	

}
