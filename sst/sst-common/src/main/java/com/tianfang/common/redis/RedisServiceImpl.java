package com.tianfang.common.redis;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**		
 * <p>Title: RedisServiceImpl </p>
 * <p>Description: 类描述:封装redis 缓存服务器服务接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月1日下午1:31:43
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Service
public class RedisServiceImpl implements RedisService {

	private static String redisCode = "utf-8";
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public long del(final String... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	@Override
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	@Override
	public void set(String key, String value, long liveTime) {
		this.set(key.getBytes(), value.getBytes(), liveTime);
	}

	@Override
	public void set(String key, String value) {
		this.set(key, value, 0L);
	}

	@Override
	public void set(byte[] key, byte[] value) {
		this.set(key, value, 0L);
	}

	@Override
	public String get(final String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				try {
					return new String(connection.get(key.getBytes()), redisCode);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
	}

	@Override
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public boolean exists(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	@Override
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	@Override
	public long dbSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	@Override
	public String ping() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {

				return connection.ping();
			}
		});
	}
}