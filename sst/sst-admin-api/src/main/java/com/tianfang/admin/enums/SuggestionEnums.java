package com.tianfang.admin.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**		
 * <p>Title: SuggestionEnums </p>
 * <p>Description: 类描述:意见反馈其他类型</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月29日下午5:36:04
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum SuggestionEnums implements Serializable{
	SUGGEST("产品建议", 1),
	ERROR("使用故障", 2),
	HELP("帮助资讯", 3);
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private int index;

	private SuggestionEnums(String name, int index) {
       this.name = name;
       this.index = index;
	}

	public static String getName(int index) {
       for (SuggestionEnums c : SuggestionEnums.values()) {
           if (c.getIndex() == index) {
               return c.name;
           }
       }
       return null;
	}
   
	public static List<SuggestionEnums> getValus(){
		SuggestionEnums[] arr = SuggestionEnums.values();
	   	List<SuggestionEnums> list = new ArrayList<SuggestionEnums>(arr.length);
	   	for (int i = 0; i < arr.length; i++){
	   		list.add(arr[i]);
	   	}
	   	return list; 
	}
}