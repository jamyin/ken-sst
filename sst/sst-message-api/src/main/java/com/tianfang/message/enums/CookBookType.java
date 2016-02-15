package com.tianfang.message.enums;

/**		
 * <p>Title: CookBookType </p>
 * <p>Description: 类描述:咨询-健康食谱:(0-常识,1-瘦身,2-食疗,3-实物归档,4-营养手册)</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月29日下午4:41:49
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum CookBookType {
	GENERAL(0,"常识"), SLIMMING(1,"瘦身"), THERAPY(2,"食疗"), FILING(3,"实物归档"), HANDBOOK(4,"营养手册");
	
	CookBookType(int index,String value){
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
		for (CookBookType d : CookBookType.values()) {
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
		for (ActivityStatus d : ActivityStatus.values()) {
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