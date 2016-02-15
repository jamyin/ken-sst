package com.tianfang.common.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class JsonUtil {

	private static Logger log = Logger.getLogger(JsonUtil.class);
	public static String getJsonStr(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj, obj.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getObjFromJson(String jsonStr, Class cls){
		Gson gson = new Gson();
		return (T) gson.fromJson(jsonStr, cls);
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObjFromJsonArray(String arrayStr, Type cls){
		Gson gson = new Gson();
		T[] results = (T[]) gson.fromJson(arrayStr, cls);
		List<T> lists = new ArrayList<T>();
		if(ArrayUtils.isEmpty(results)){
			return lists;
		}
		
		for(T result : results){
			lists.add(result);
		}
		//return gson.fromJson(arrayStr, cls);
		return lists;
	}
	
	/**
	 * @param date
	 * @param format
	 * @return
	 * @2015年7月28日
	 */
	public static Long parseDateStr(String date, String format){
		Long millions = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date day = sdf.parse(date);
			millions = day.getTime();
		} catch (ParseException e) {
			log.error("时间转换出错——>parseDateStr()", e);
		}
		return millions;
	}
	
	/**
	 * 将日期转换成指定格式的字符串
	 * @param date
	 * @param pattern
	 * @return
	 * @2015年8月17日
	 */
	public static String parseDateStr(Date date, String pattern){
		String result = new String();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			result = sdf.format(date);
		} catch (Exception e) {
			log.error("格式化时间戳格式字符串发生异常parseDateStr", e);
		}
		return result;
	}
	
	/**
	 * 判断字符串是否是空或空串<br>
	 * return true just when the param <b>str</b> is not null or ""
	 * @param str
	 * @return
	 * @2015年7月29日
	 */
	public static boolean isNotEmpty(String str){
		boolean flag = false;
		if((str != null) && (!str.equals(""))){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 获取@Table(name="xx")注解的name属性值
	 * @param clazz
	 * @return
	 * @2015年7月30日
	 */
//	public static String getTableName(Class<?> clazz){
//		String tableName = null;
//		Table ano = (Table) clazz.getAnnotation(Table.class);
//		if(ano != null){
//			tableName = ano.name();
//		}
//		return tableName;
//	}
	
	/**
	 * 返回指定日期所在月份的第一天是星期几<br>
	 * @param date
	 * @param pattern
	 * <br><b>date</b>为时间戳格式的日期；<b>pattern</b>为时间戳的格式，
	 * 如yyyy-MM-dd HH:mm:ss,yyyyMMdd HH:mm:ss
	 * @return
	 * @2015年8月17日
	 */
	public static Integer getWeekDay(String date, String pattern){
		Integer number = 0;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date dat = sdf.parse(date);
			// 获取指定日期所在月的第一天
			String fDateStr = (new SimpleDateFormat("yyyy-MM")).format(dat) + "-01";
			Date fDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(fDateStr);
			cal.setTime(fDate);
			number = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if(number == 0){
				throw new Exception("时间戳格式字符串转换错误");
			}
		} catch (Exception e) {
			log.error("格式化时间戳格式字符串发生异常getWeekDay", e);
		}
		return number;
	}
	
	/**
	 * 格式化指定时间戳格式的字符串
	 * @param date
	 * @param apattern
	 * @param bpattern
	 * <br>apattern表示字符串的格式；bpattern表示要返回的格式
	 * @return
	 * @2015年8月17日
	 */
	public static String getYMD(String date, String apattern, String bpattern){
		String result = new String();
		SimpleDateFormat sdf = new SimpleDateFormat(apattern);
		try {
			Date dat = sdf.parse(date);
			SimpleDateFormat sdfb = new SimpleDateFormat(bpattern);
			result = sdfb.format(dat);
		} catch (Exception e) {
			log.error("格式化时间戳格式字符串发生异常getYMD", e);
		}
		return result;
	}

	/**
	 * 返回指定日期所在月的前（后）n个月，比如2015-07-07所在月的上个月的第一天
	 * @param date
	 * @param apattern
	 * @param bpattern
	 * @param n
	 * @return
	 * @2015年8月17日
	 */
	public static String getSMDate(String date, String apattern, String bpattern, int n){
		String result = new String();
		SimpleDateFormat sdf = new SimpleDateFormat(apattern);
		try {
			Date dat = sdf.parse(date);
			String fdatestr = (new SimpleDateFormat("yyyy-MM")).format(dat) + "-01";
			Date fdate = (new SimpleDateFormat("yyyy-MM-dd")).parse(fdatestr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fdate);
			cal.add(Calendar.MONTH, n);
			Date rdate = cal.getTime();
			result = (new SimpleDateFormat(bpattern)).format(rdate); 
		} catch (Exception e) {
			log.error("格式化时间戳格式字符串发生异常getSMDate", e);
		}
		return result;
	}
		
	/**
	 * 将时间戳（秒）格式化
	 * @param seconds
	 * @param pattern
	 * @return
	 * @2015年9月8日
	 */
	public static String parseTimestamp(Long seconds, String pattern){
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(seconds*1000);
		Date date = cl.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Date()+"--"+new Date().getTime());
			
		
	}
	
}
