package com.tianfang.admin.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**		
 * <p>Title: RaceType </p>
 * <p>Description: 类描述:比赛赛事中比赛类型枚举变量</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月14日下午5:34:43
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum RaceType implements Serializable{
	FRIENDLY("友谊赛 ", 1), WARMUP("热身赛 ", 2), REGULATION("正式比赛", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private RaceType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (RaceType c : RaceType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    
    public static List<RaceType> getValus(){
    	RaceType[] arr = RaceType.values();
    	List<RaceType> list = new ArrayList<RaceType>(arr.length);
    	for (int i = 0; i < arr.length; i++){
    		list.add(arr[i]);
    	}
    	return list; 
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}