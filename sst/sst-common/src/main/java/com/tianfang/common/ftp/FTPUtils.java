package com.tianfang.common.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.tianfang.common.util.PropertiesUtils;


public class FTPUtils {
	
	private static String URL = PropertiesUtils.getProperty("ftp.url");
	
	private static String PORT = PropertiesUtils.getProperty("ftp.port");
	
	private static String USERNAME = PropertiesUtils.getProperty("ftp.username");
	
	private static String PASSWORD = PropertiesUtils.getProperty("ftp.password");
	
	private static String PATH = PropertiesUtils.getProperty("ftp.path");
	

	/**
	* Description: 向FTP服务器上传文件
	* @param path FTP服务器保存目录
	* @param filename 上传到FTP服务器上的文件名
	* @param input 输入流
	* @return 成功返回true，否则返回false
	*/ 
	public static boolean uploadFile(String path, String filename, InputStream input) { 
	    boolean success = false; 
	    FTPClient ftp = new FTPClient(); 
	    try { 
	        int reply; 
	        ftp.connect(URL, Integer.parseInt(PORT));//连接FTP服务器 
	        ftp.login(USERNAME, PASSWORD);//登录 
	        reply = ftp.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) { 
	            ftp.disconnect(); 
	            return success; 
	        } 
	        ftp.setFileType(FTP.BINARY_FILE_TYPE);  
	        ftp.changeWorkingDirectory(PATH + path); 
	        ftp.storeFile(filename, input);          
	         
	        input.close(); 
	        ftp.logout(); 
	        success = true; 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        if (ftp.isConnected()) { 
	            try { 
	                ftp.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success; 
	}
	
	
	
	public static void main(String[] args) {
		try { 
			URL ="192.168.1.175";
			PORT = "21";
			USERNAME = "sport";
			PASSWORD = "123456";
			PATH = "/home/sport/";
	        FileInputStream in=new FileInputStream(new File("D:/aaa.jpg")); 
	        boolean flag = uploadFile("image", "aaa.jpg", in); 
	        System.out.println(flag); 
	    } catch (FileNotFoundException e) { 
	        e.printStackTrace(); 
	    }  
		
	}
}
