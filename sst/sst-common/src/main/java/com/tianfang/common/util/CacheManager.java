package com.tianfang.common.util;

import com.google.common.cache.LoadingCache;
import java.util.List;


public abstract class CacheManager {

    /**
     * 刷新缓存
     * @param cache 缓存对象
     * @param keys 缓存的key
     */
    public static void refresh(LoadingCache<Long, ?> cache, List<Long> keys){
        if (keys == null || keys.size() <= 0)
            return;
        refresh(cache, keys.toArray(new Long[]{}));
    }

    /**
     * 刷新缓存
     * @param cache 缓存对象
     * @param keys 缓存key
     */
    public static void refresh(LoadingCache<Long, ?> cache, Long... keys){
        if (cache != null && keys != null && keys.length > 0){
            for (Long key : keys){
                cache.refresh(key);
            }
        }
    }

    /**
     * 删除缓存
     * @param cache 缓存对象
     * @param keys 缓存key
     */
    public static void invalidate(LoadingCache<Long, ?> cache, List<Long> keys){
        if (keys == null || keys.size() <= 0)
            return;
        invalidate(cache, keys.toArray(new Long[]{}));
    }

    /**
     * 删除缓存
     * @param cache 缓存对象
     * @param keys 缓存key
     */
    public static void invalidate(LoadingCache<Long, ?> cache, Long... keys){
        if (cache != null && keys != null && keys.length > 0){
            for (Long key : keys){
                cache.invalidate(key);
            }
        }
    }
}
