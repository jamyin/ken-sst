package com.tianfang.admin.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: VideoTypeEnums </p>
 * <p>Description: 类描述:视频类型枚举变量(1-Happy Football,2-精英训练营,3-原创视频)</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月22日下午2:25:21
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum VideoTypeEnums implements Serializable{
	NOT("无", -1), HAPPY("Happy Football", 1), CAMP("精英训练营 ", 2), ORIGINAL("原创视频", 3);
    
	@Getter
	@Setter
	private String name;
    
	@Getter
	@Setter
	private int index;

    // 构造方法
    private VideoTypeEnums(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (VideoTypeEnums c : VideoTypeEnums.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    
    public static List<VideoTypeEnums> getValus(){
    	VideoTypeEnums[] arr = VideoTypeEnums.values();
    	List<VideoTypeEnums> list = new ArrayList<VideoTypeEnums>(arr.length);
    	for (int i = 0; i < arr.length; i++){
    		list.add(arr[i]);
    	}
    	return list; 
    }
}