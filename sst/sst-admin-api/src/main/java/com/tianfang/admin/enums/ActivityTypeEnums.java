package com.tianfang.admin.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: ActivityTypeEnums </p>
 * <p>Description: 类描述:视图模板(0-未来之星,1-主题活动,2-精英训练营,3-课程内容,4-学院理念)</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月22日下午2:20:28
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum ActivityTypeEnums implements Serializable{
	DEFAULT("无", -1, ""),
	STAR("未来之星首页", 0, "/futurestar/star"),
	STARDETAIL("未来之星详情", 1, "/futurestar/futurestar_detail"),
	SUBJECT("主题活动首页", 2, "/futurestar/subject"),
	SUBJECTDETAIL("主题活动详情",3,"/futurestar/subject_detail"),
	CAMP("精英训练营", 4, "/futurestar/futurestar_elite");
	
	@Getter
	@Setter
    private String name;
	
	@Getter
	@Setter
	private int index;
    
	@Getter
	@Setter
	private String view; // modelAndView 视图路径

    // 构造方法
    private ActivityTypeEnums(String name, int index, String view) {
        this.name = name;
        this.index = index;
        this.view = view;
    }

    // 普通方法
    public static String getName(int index) {
        for (ActivityTypeEnums c : ActivityTypeEnums.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    
    public static String getView(int index){
    	  for (ActivityTypeEnums c : ActivityTypeEnums.values()) {
              if (c.getIndex() == index) {
                  return c.view;
              }
          }
          return null;
    }
    
    public static List<ActivityTypeEnums> getValus(){
    	ActivityTypeEnums[] arr = ActivityTypeEnums.values();
    	List<ActivityTypeEnums> list = new ArrayList<ActivityTypeEnums>(arr.length);
    	for (int i = 0; i < arr.length; i++){
    		list.add(arr[i]);
    	}
    	return list; 
    }
}