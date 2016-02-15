package com.tianfang.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ObjectUtils {
	/**
	 * 
		 * 此方法描述的是：List to Map
		 * @author: cwftalus@163.com
		 * @version: 2015年4月7日 下午6:11:56
	 */
	public static <T> HashMap<T,T> ListToMap(List<T> result,T key){
		HashMap<T,T> hashMap = new HashMap<T, T>();
		Iterator<T> its = (Iterator<T>) result.iterator();
		while(its.hasNext()){
			T t = its.next();
			hashMap.put(key, t);
		}
		return hashMap;
	}
}
