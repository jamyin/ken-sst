package com.tianfang.common.mybatis;

import java.util.Date;

import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.common.util.reflect.DynamicMethod;

public abstract class MyBatisBaseDao<T> {
	
	public int deleteByPrimaryKey(String id){
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "deleteByPrimaryKey", new Object[]{id});
	}
	
	@SuppressWarnings("unchecked")
	public T selectByPrimaryKey(String id){
		return (T)DynamicMethod.invokeMethod(getMapper(), "selectByPrimaryKey", new Object[]{id});
	}
	
	public int insert(T obj){
		Object id = DynamicMethod.invokeMethod(obj, "getId");
		if(id == null || StringUtils.isEmpty(((String)id).trim())){
			DynamicMethod.invokeMethod(obj, "setId", new Object[]{UUIDGenerator.getUUID()});
		}
		DynamicMethod.invokeMethod(obj, "setCreateTime", new Object[]{new Date()});
		DynamicMethod.invokeMethod(obj, "setStat", new Object[]{new Integer(1)});
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "insert", new Object[]{obj});
	}
	
	public int updateByPrimaryKey(T obj){
		DynamicMethod.invokeMethod(obj, "setLastUpdateTime", new Object[]{new Date()});
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "updateByPrimaryKey", new Object[]{obj});
	}
	
	public int insertSelective(T obj){
		Object id = DynamicMethod.invokeMethod(obj, "getId");
		if(id == null || StringUtils.isEmpty(((String)id).trim())){
			DynamicMethod.invokeMethod(obj, "setId", new Object[]{UUIDGenerator.getUUID()});
		}
		DynamicMethod.invokeMethod(obj, "setCreateTime", new Object[]{new Date()});
		DynamicMethod.invokeMethod(obj, "setStat", new Object[]{new Integer(1)});
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "insertSelective", new Object[]{obj});
	}
	
	public int updateByPrimaryKeySelective(T obj){
		DynamicMethod.invokeMethod(obj, "setLastUpdateTime", new Object[]{new Date()});
		return (Integer)DynamicMethod.invokeMethod(getMapper(), "updateByPrimaryKeySelective", new Object[]{obj});
	}
	
	public abstract Object getMapper();
}
