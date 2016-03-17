package com.tianfang.admin.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: MenuTypeEnums </p>
 * <p>Description: 类描述:前台菜单级别枚举</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月22日下午2:20:34
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum MenuTypeEnums implements Serializable{
	ONE("一级 ", 1), TWO("二级 ", 2), three("三级", 3), FOUR("四级", 4),FIVE("五级 ", 5);
    
	@Getter
	@Setter
	private String name;
    
	@Getter
	@Setter
	private int index;

    // 构造方法
    private MenuTypeEnums(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (MenuTypeEnums c : MenuTypeEnums.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    
    public static List<MenuTypeEnums> getValus(){
    	MenuTypeEnums[] arr = MenuTypeEnums.values();
    	List<MenuTypeEnums> list = new ArrayList<MenuTypeEnums>(arr.length);
    	for (int i = 0; i < arr.length; i++){
    		list.add(arr[i]);
    	}
    	return list; 
    }
}