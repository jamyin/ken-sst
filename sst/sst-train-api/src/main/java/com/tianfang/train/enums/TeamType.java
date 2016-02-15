package com.tianfang.train.enums;

public enum TeamType {
	NOT(0,"未开始"), ING(1,"进行中"), OVER(2, "已结束");
	
	TeamType(int index,String value){
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
		for (TeamType d : TeamType.values()) {
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
		for (AuditType d : AuditType.values()) {
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