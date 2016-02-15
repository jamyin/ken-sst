package com.tianfang.train.enums;

/**		
 * <p>Title: UserType </p>
 * <p>Description: 类描述:审核状态 0-未审核,1-通过,2-拒绝</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月21日下午3:22:33
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public enum AuditType {
	UNAUDIT(0,"未审核"), PASS(1,"通过"), FAIL(2, "拒绝");
	
	AuditType(int index,String value){
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
		for (AuditType d : AuditType.values()) {
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