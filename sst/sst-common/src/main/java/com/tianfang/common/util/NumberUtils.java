package com.tianfang.common.util;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils{
	
	private static final Pattern isIntegerPattern = Pattern.compile("([1-9][0-9]*)|(0)"); 
	
	private static final Pattern isNumericPattern = Pattern.compile("(([1-9][0-9]*)|(0))([.][0-9]+)?");
	

	/**
	 * 判断字符串是否是整型数字
	 * @param viaPort
	 * @return
	 */
	public static boolean isInteger(String str) {
		if(str != null){
			return isIntegerPattern.matcher(str.trim()).matches();  
		}else{
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点型数字
	 * @param allPrice
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if(str != null){
			return isNumericPattern.matcher(str.trim()).matches();  
		}else{
			return false;
		}
	}
	
	public static double formatNumber(double value) {
		DecimalFormat format = new java.text.DecimalFormat("##########.##");
		return Double.valueOf(format.format(value));
	}
	
	public static DecimalFormat createNumberFormat(){
		DecimalFormat format = new java.text.DecimalFormat("##########.##");
		return format;
	}
	
	public static void main(String[] args) {
		DecimalFormat df = new java.text.DecimalFormat("##########.##");
		System.out.println(df.format(12342232.532));
	}

}
