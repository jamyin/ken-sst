package com.tianfang.common.constants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PlayerPosition implements Serializable{
	
	public final static Map<String,String> statusMap = new HashMap<String,String>() {
		private static final long serialVersionUID = 1L;
		{
			put(String.valueOf(DataStatus.ENABLED), "有效");
			put(String.valueOf(DataStatus.DISABLED), "无效");
		}
	};
	
	
	public final static Map<String,String> formationMap = new HashMap<String,String>() {
		private static final long serialVersionUID = 1L;

		{
			put("3-3-4", "334.jpg");
			put("3-5-2", "352.jpg");
			put("4-2-4", "424.jpg");
			put("4-3-3", "433.jpg");
			put("4-4-2", "442.jpg");
			put("4-5-1", "451.jpg");
		}
	};
	
	public final static Map<String,String> map = new HashMap<String,String>() {
		
			/**
			 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
			 *
			 * @since Ver 1.1
			 */
		private static final long serialVersionUID = 1L;

		{
			put("gk", "守门员");
			put("sw", "清道夫");
			put("lwb", "左边后卫");
			put("lb", "左后卫");
			put("lcb", "左中后卫");
			put("cb", "中后卫");
			put("rwb", "右边后卫");
			put("rb", "右后卫");
			put("rcb", "右中后卫");
			put("ab", "攻击型后卫");
			put("cdm", "后腰");
			put("lwm", "左边中场");
			put("lm", "左中场");
			put("lcm", "左中中场");
			put("cm", "中中场");
			put("lwm", "右边中场");
			put("lm", "右中场");
			put("lcm", "右中中场");
			put("cam", "前腰");
			put("lf", "左前锋");
			put("cf", "中前锋");
			put("rf", "右前锋");
			put("ls", "左中锋");
			put("st", "中锋");
			put("rs", "右中锋");
		}
	};
}
