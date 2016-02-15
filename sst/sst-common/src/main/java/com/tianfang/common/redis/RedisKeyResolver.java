package com.tianfang.common.redis;

import org.springframework.stereotype.Component;

import com.tianfang.common.exception.SystemException;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.reflect.DynamicMethod;
import com.tianfang.common.util.reflect.ReflectStringUtil;

@Component
public class RedisKeyResolver<T> {

	private static final String SPLIT_CHAR = ":";
	
	/**
	 * 获取RedisKeyPrefix annotation
	 * @param cls
	 * @return
	 */
	public RedisKeyPrefix getRedisKeyPrefix(Class<? extends Object> cls) {
		RedisKeyPrefix prefix = (RedisKeyPrefix) cls.getAnnotation(RedisKeyPrefix.class);
		return prefix;
	}
	
	/**
	 * 根据对象类型和值获取key值
	 * @param cls
	 * @param value
	 * @return
	 */
	public String getRedisKey(Class<? extends Object> cls, String value) {
		RedisKeyPrefix keyPrefix = this.getRedisKeyPrefix(cls);
		return this.paseKey(keyPrefix, value);
	}
	
	//------获取单对象存储键值
	public String paseKey(RedisKeyPrefix prefix, String value){
		if(StringUtils.isEmpty(prefix.prefixValue())){
			throw new SystemException("prefixValue can not be null");
		}
		return populateKey(prefix, value); 
	}
	
	public String paseKey(RedisKeyPrefix prefix, T t) {
		if(StringUtils.isEmpty(prefix.prefixValue())){
			throw new SystemException("prefixValue can not be null");
		}
		String[] keyParams = StringUtils.split(prefix.prefixValue(), SPLIT_CHAR);
		if(keyParams.length != 3){
			throw new SystemException("prefixValue 格式错误");
		}
		String keyFiled = keyParams[2];
		String keyFiledValue = DynamicMethod.invokeMethod(t, ReflectStringUtil.populateGetMethodName(keyFiled)).toString();
		return populateKey(prefix, keyFiledValue);
	}
	

	private String populateKey(RedisKeyPrefix prefix, String value) {
		return prefix.prefixValue() + SPLIT_CHAR + value;
	}
	
	//------获取List存储的键值
	
	public String paseKeyForList(RedisKeyPrefix prefix, String value){
		if(StringUtils.isEmpty(prefix.prefixValue())){
			throw new SystemException("prefixValue can not be null");
		}
		return populateKeyForList(prefix, value); 
	}
	
	public String paseKeyForList(RedisKeyPrefix prefix, T t) {
		if(StringUtils.isEmpty(prefix.prefixValue())){
			throw new SystemException("prefixValue can not be null");
		}
		String[] keyParams = StringUtils.split(prefix.prefixValue(), SPLIT_CHAR);
		if(keyParams.length != 3){
			throw new SystemException("prefixValue 格式错误");
		}
		String keyFiled = keyParams[2];
		String keyFiledValue = DynamicMethod.invokeMethod(t, ReflectStringUtil.populateGetMethodName(keyFiled)).toString();
		return populateKeyForList(prefix, keyFiledValue);
	}
	
	private String populateKeyForList(RedisKeyPrefix prefix, String keyFiledValue) {
		return prefix.prefixValue() + SPLIT_CHAR + keyFiledValue + SPLIT_CHAR + "forlist";
	}

}
