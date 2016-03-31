package com.tianfang.common.constants;

import lombok.Getter;
import lombok.Setter;

public enum TeamPlayerPositionEnum {
	GK(1, "守门员"),
	SW(2, "清道夫"),
	LWB(3, "左边后卫"),
	LB(4, "左后卫"),
	LCB(5, "左中后卫"),
	CB(6, "中后卫"),
	RWB(7, "右边后卫"),
	RB(8, "右后卫"),
	RCB(9, "右中后卫"),
	AB(10, "攻击型后卫"),
	PUT(11, "后腰"),
	LWM(12, "左边中场"),
	LM(13, "左中场"),
	LCM(14, "左中中场"),
	CM(15, "中中场"),
	RWM(16, "右边中场"),
	RM(17, "右中场"),
	RCM(18, "右中中场"),
	CAM(19, "前腰"),
	LF(20, "左前锋"),
	CF(21, "中前锋"),
	RF(22, "右前锋"),
	LS(23, "左中锋"),
	ST(24, "中锋"),
	RS(25, "右中锋");
   
	TeamPlayerPositionEnum(){
	}
	@Getter
	@Setter
	private Integer index;
	
	@Getter
	@Setter
	private String valString;
	
	TeamPlayerPositionEnum(Integer index, String valString) {
		this.index = index;
		this.valString = valString;
	}
	
	// 普通方法
    public static String getName(int index) {
        for (TeamPlayerPositionEnum c : TeamPlayerPositionEnum.values()) {
            if (c.getIndex() == index) {
                return c.valString;
            }
        }
        return null;
    }
    
}
