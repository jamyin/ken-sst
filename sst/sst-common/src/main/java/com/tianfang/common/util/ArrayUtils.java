package com.tianfang.common.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {

	public static List<byte[]> splitByteArray(byte[] all, int len){
		List<byte[]> list = new ArrayList<byte[]>();
		for(int i = 0; i < all.length; i = i + len){
			int end = i + len;
			if(end >= all.length){
				end = all.length;
			}
			byte[] temp = ArrayUtils.subarray(all, i, end);
			list.add(temp);
			
		}
		return list;
	}

}
