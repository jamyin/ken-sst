package com.tianfang.common.util;

import java.util.Random;

/**
 * Created by pc-tank on 14-9-15.
 */
public class RandomCode {

	/**
	 * 随机生成编码
	 * 
	 * @param digit
	 *            位数
	 * @return 随机码
	 */
	public static String getRandomCode(int digit) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digit; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getEnRandomCode(int digit){
		String base = "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digit; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	
	/**
	 * 此方法描述的是：
	 * @author: cwftalus@163.com
	 * @version: 2015年4月1日 下午4:28:50
	 */
	public static String getRandomCodeByNumber(int digit) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digit; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
