package com.tianfang.common.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class WdRedisDao<T> {
	
    protected static final Log logger = LogFactory.getLog(WdRedisDao.class);
    
    public static final long DEFAULT_TIME_OUT = 30;
	
    @Autowired
	private RedisTemplate<String, T> redisTemplate;
	
	@Autowired
	private RedisKeyResolver<T> keyResolver;
	
	/**
	 * 将对象推入Redis
	 * @param t
	 */
	public void set(T t){
		RedisKeyPrefix prefix = keyResolver.getRedisKeyPrefix(t.getClass());
		if(prefix == null){
			return;
		}
		String key = keyResolver.paseKey(prefix, t);
		logger.debug("put into redis --- key eq : " + key + " and value eq " + t);
		redisTemplate.opsForValue().set(key, t);
	}
	
	
	/**
	 * 将对象推入Redis, 带失效时间
	 * @param t	对象
	 * @param timeout 失效时间，单位分钟
	 */
	public void set(T t, long timeout){
		RedisKeyPrefix prefix = keyResolver.getRedisKeyPrefix(t.getClass());
		if(prefix == null){
			return;
		}
		String key = keyResolver.paseKey(prefix, t);
		logger.debug("put into redis --- key eq : " + key + " and value eq " + t);
		redisTemplate.opsForValue().set(key, t, timeout, TimeUnit.MINUTES);;
	}
	
	public void setAsDefaultTimeOut(T t){
		set(t, DEFAULT_TIME_OUT);
	}
	
	/**
	 * 根据对象的key配置获取一个对象
	 * @param keyValue
	 * @param cls
	 * @return
	 */
	public T get(String keyValue, Class<?> cls){
		RedisKeyPrefix prefix = keyResolver.getRedisKeyPrefix(cls);
		if(prefix == null){
			return null;
		}
		String key = keyResolver.paseKey(prefix, keyValue);
		logger.debug("get from redis --- key eq : " + key );
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 将一个对象从队列的左边推入
	 * @param t
	 */
	public void setToList(T t){
		RedisKeyPrefix prefix = keyResolver.getRedisKeyPrefix(t.getClass());
		if(prefix == null){
			return;
		}
		String key = keyResolver.paseKeyForList(prefix, t);
		logger.debug("put into redis list --- key eq : " + key + " and value eq " + t);
		redisTemplate.opsForList().leftPush(key, t);
	}
	
	public void setToList(List<T> list) {
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		
		for(T record : list){
			setToList(record);
		}
	}
	
	public void setToList(String key, T t){
		redisTemplate.opsForList().leftPush(key, t);
	}
	
	/**
	 * 获取队列中的所有对象的List
	 * @param keyValue
	 * @param cls
	 * @return
	 */
	public List<T> getList(String keyValue, Class<?> cls) {
		RedisKeyPrefix prefix = keyResolver.getRedisKeyPrefix(cls);
		if(prefix == null){
			return null;
		}
		String key = keyResolver.paseKeyForList(prefix, keyValue);
		logger.debug("get all from redis list --- key eq : " + key );
		
		List<T> list = new ArrayList<T>();
		BoundListOperations<String, T> opt = redisTemplate.boundListOps(key);
		long size = opt.size();
		for(int index = 0; index < size; index++){
			T t = opt.index(index);
			list.add(t);
		}
		return list;
	}
	
	/**
	 * 从队列中取出一个对象
	 * @param keyValue 队列的key
	 * @return
	 */
	public T pop(String keyValue){
		logger.debug("pop a obj from redis list --- key eq : " + keyValue );
		return redisTemplate.opsForList().rightPop(keyValue);
	}
	
	/**
	 * 从队列中取出一个对象, 并设置失效时间
	 * @param keyValue
	 * @param timeout
	 * @return
	 */
	public T bpop(String keyValue, long timeout) {
		logger.debug("pop a obj from redis list --- key eq : " + keyValue + " timeout : " + timeout);
		return redisTemplate.opsForList().rightPop(keyValue, timeout, TimeUnit.MINUTES);		
	}	
	
	public void delete(List<String> keys){
		redisTemplate.delete(keys);
	}
	
	public void delete(String key){
		redisTemplate.delete(key);
	}
	
	public Boolean hasKey(String key){
		return redisTemplate.hasKey(key);
	}

}
