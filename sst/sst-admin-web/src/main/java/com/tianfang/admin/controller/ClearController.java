package com.tianfang.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.tianfang.common.constants.CacheKey;
import com.tianfang.common.model.Response;
import com.tianfang.common.redis.RedisService;

@Controller
public class ClearController extends BaseController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/clearCache")
	@ResponseBody
	public Response<String> clearCache(String cacheType) {
		Response<String> data = new Response<String>();
		String userId = getSessionUserId();
		if(Objects.equal(cacheType, "admin")){
			//清楚后台缓存
			redisTemplate.delete(CacheKey.CACHE_ADMIN_LEFT_MENU+userId);
			
		}else if(Objects.equal(cacheType, "front")){
			//清楚后台缓存
			redisTemplate.delete(CacheKey.CACHE_ADMIN_LEFT_MENU+userId);
		}else if (Objects.equal(cacheType, "redis")){
			// 清除redis缓存
			String result = redisService.flushDB();
			
			System.out.println("redis缓存清除"+result);
		}
		
		return data;
	}
}
