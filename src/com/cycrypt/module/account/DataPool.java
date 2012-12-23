package com.cycrypt.module.account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

import com.cycrypt.utils.FilePathUtil;

public class DataPool implements Runnable {

	private static DataPool instance = null;
	private static LinkedList<String> accountStrList;
	
	private Thread writingThread;
	
	private FileWriter fw;

	private static final String PERSIST_FILE_NAME="AFILE.txt";
	
	
	private void initFW() throws IOException
	{
		File f = new File(FilePathUtil.getRunningPath()+File.separatorChar+PERSIST_FILE_NAME);
		if(!f.exists())
			f.createNewFile();
		fw = new FileWriter(f,true);
	}
	
	
	private DataPool()
	{
		this.accountStrList = new LinkedList<String>();
		try {
			initFW();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DataPool getInstance()
	{
		if(instance==null)
			instance = new DataPool();
		return instance;
	}
	
	public synchronized boolean addData(String str)
	{
		this.accountStrList.offer(str);
		if(this.fw==null)
		{
			return false;
		}
		if(writingThread==null)
		{
			writingThread = new Thread(this);
			writingThread.start();
		}
		this.notifyAll();
		return true;
	}
	
	private synchronized void writeData() throws InterruptedException, IOException
	{
		if(this.accountStrList.isEmpty())
		{
			this.wait();
		}else
		{
			String data = this.accountStrList.pop();
			this.fw.write(data+"\r\n");
			this.fw.flush();
		}
	}
	
	public synchronized String getLastLine()
	{
		RandomAccessFile rf = null;  
		String result=null;
		//System.out.println(new Date().getTime());  
		File persistFile = new File(FilePathUtil.getRunningPath()+File.separatorChar+PERSIST_FILE_NAME);
		if(!persistFile.exists())
		{
			return null;	
		}
		try {  
		    rf = new RandomAccessFile(persistFile, "r");  
		    long len = rf.length();  
		    long start = rf.getFilePointer();  
		    long nextend = start + len - 1;  
		    String line = "";  
		    rf.seek(nextend);  
		    int c = -1;  
		    int t = 0;  
		    while (nextend > start) {  
		        c = rf.read();  
		        if (c == '\n' || c == '\r') {  
		            line = rf.readLine();  
		            t++;  
		            if (t >= 1 && line != null) {  
		                result = line;  
		                break;
		            }  
		            nextend--;  
		        }  
		        nextend--;  
		        rf.seek(nextend);  
		        if (nextend == 0) {// 当文件指针退至文件开始处，输出第一行  
		            result = line; 
		        }  
		    }  
		} catch (FileNotFoundException e) {  
		            e.printStackTrace();  
		} catch (IOException e) {  
		            e.printStackTrace();  
		} finally {  
		    try {  
		        if (rf != null)  
		            rf.close();  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }  
		}  
		return result;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			try {
				while(true)
				{
					writeData();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					this.fw.close();
					this.fw = null;
//					throw new RuntimeException(""):
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
}
