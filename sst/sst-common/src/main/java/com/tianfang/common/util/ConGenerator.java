/**
 * 
 */
package com.tianfang.common.util;

/**
 * 用于切分字符串，生成in查询条件
 * @author wk.s
 * 2015年12月7日
 */
public class ConGenerator {

	/**
	 * 切割字符串，组装('a','b',....)类型的sql条件
	 * @param str "a"或"a,"型字符串
	 * @param regex 分隔符
	 * @return
	 */
	public static String getCons(String str, String regex){
		
		StringBuffer re = new StringBuffer();
		String[] strArr = str.split(regex);
		Integer length = strArr.length;
		if(length > 0){
			re.append("(");
			for(int i = 0; i < length; i++){
				if((i + 1) < length){
					re.append("'").append(strArr[i]).append("'").append(",");
				}else{
					re.append("'").append(strArr[i]).append("'");
				}
			}
			re.append(")");
			return re.toString();
		}else{
			return null;
		}
	}
	
	
}
