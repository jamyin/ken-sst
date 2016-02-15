package com.tianfang.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author rkzhang
 *
 */
public abstract class StringUtils extends org.apache.commons.lang.StringUtils{

	static String[] videoPrex = {"wmv","asf","asx","rm","rmvb","mpg","mpeg","mpe","dat","vob","dv","3gp","3g2","mov","avi","mkv","mp4","m4v","flv"};
	static String flvVideo = "flv";
	static String[] spePicSuff = {"gif","png","jpg"};
	
    /**
     * 订单生成格式
     */
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    
    //判断是否是视频格式
    public static Boolean isVideo(String prefix){
    	for(String str : videoPrex){
    		if(str.equals(prefix.toLowerCase())){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 验证文件后缀名是否是flv
     * <br>如果是flv格式，返回true；否则false
     * @param suffix
     * @author wk.s
     * @return
     */
    public static Boolean isFlvVideo(String suffix){
    	
    	Boolean flag = true;
    	if(!suffix.equals(flvVideo)){
    		flag = false;
    	}
    	return flag;
    }
    
    /**
     * 验证后缀名是否是指定的图片后缀名
     * <br>如果是指定格式的图片后缀，返回true；否则返回false
     * @param suffix
     * @author wk.s
     * @return
     */
    public static Boolean isSpecPic(String suffix){
    	
    	Boolean flag = false;
    	for (String str : spePicSuff) {
			if(str.equals(suffix)){
				flag = true;
				break;
			} 
		}
    	return flag;
    }
    
    public static String buildOrderNo(){
        return sdf.format(new Date());
    }

	public static List<byte[]> splitString(String data, int len){
		byte[] all = data.getBytes();
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

	public static boolean startsWith(String methodName, String[] prefixs) {
		
		if(StringUtils.isEmpty(methodName) || ArrayUtils.isEmpty(prefixs)) {
			return false;
		}
		
		for(String prefix : prefixs) {
			if(methodName.startsWith(prefix)) {
				return true;
			}
		}
		
		return false;
	}
	
	
}
