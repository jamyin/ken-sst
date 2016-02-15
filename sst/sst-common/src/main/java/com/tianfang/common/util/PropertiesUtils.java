package com.tianfang.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PropertiesUtils {
	
	protected static final Log logger = LogFactory.getLog(PropertiesUtils.class);
	
	private static Properties prop = new Properties();
	
	static {
		InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static String getProperty(String propertyKey) {
		String str = "";
		if(prop.isEmpty()){
			return str;
		}
		
		String url = prop.getProperty(propertyKey);
		try {
			str = new String(url.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
		return str;
	}
	
	public static void printPropertyInfo(){
		for(Object key : prop.keySet()){
			logger.info(key + " = " + prop.getProperty(key.toString()));
		}
	}
}
