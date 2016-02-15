package com.tianfang.common.redis;

import java.util.Set;

/**		
 * <p>Title: RedisService </p>
 * <p>Description: 类描述:redis 的操作开放接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2015年12月1日下午1:20:08
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface RedisService {
	
    /**
     * 通过key删除
     * @param keys
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:21:47
     */
    public abstract long del(String... keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     * @param key
     * @param value
     * @param liveTime
     * @author xiang_wang
     * 2015年12月1日下午1:21:58
     */
    public abstract void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime 单位秒
     * @author xiang_wang
     * 2015年12月1日下午1:22:11
     */
    public abstract void set(String key, String value, long liveTime);

    /**
     * 添加key value
     * @param key
     * @param value
     * @author xiang_wang
     * 2015年12月1日下午1:22:30
     */
    public abstract void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     * @param key
     * @param value
     * @author xiang_wang
     * 2015年12月1日下午1:22:40
     */
    public abstract void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     * @param key
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:22:49
     */
    public abstract String get(String key);

    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:22:58
     */
    public abstract Set<String> keys(String pattern);

    /**
     * 检查key是否已经存在
     * @param key
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:23:45
     */
    public abstract boolean exists(String key);

    /**
     * 清空redis 所有数据
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:23:55
     */
    public abstract String flushDB();

    /**
     * 查看redis里有多少数据
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:24:05
     */
    public abstract long dbSize();

    /**
     * 检查是否连接成功
     * @return
     * @author xiang_wang
     * 2015年12月1日下午1:24:12
     */
    public abstract String ping();
}