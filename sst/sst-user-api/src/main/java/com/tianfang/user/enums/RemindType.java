package com.tianfang.user.enums;

/**		
 * <p>Title: RemindType </p>
 * <p>Description: 类描述:提醒类型枚举 1-电话,2-短信,3-应用内</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月22日上午9:41:48
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum RemindType {

	MOBILE(1,"电话"), SMS(2,"短信"), APP(3, "应用内");
	
	RemindType(int index,String value){
		this.index =index;
		this.value =value;
	}
	
	private int index;
	
	private String value;
	/**
	 * 根据index获取value
	 * @param index
	 * @return
	 */
	public static String getByIndexValue(int index){
		for (RemindType d : RemindType.values()) {
			if (d.getIndex() == index) {
				return d.value;
			}
		}
		return null;
	}
	/**
	 * 根据value获取id
	 * @param value
	 * @return
	 */
	public static int getByValueIndex(String value){
		for (RemindType d : RemindType.values()) {
			if(d.getValue().equals(value)){
				return d.getIndex();
			}
		}
		return 0;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}