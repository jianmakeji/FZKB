package com.jianma.fzkb.cache.redis.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.cache.redis.InitCache;

@Repository
@Component
@Qualifier(value = "initCacheImpl")
public class InitCacheImpl implements InitCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOperations;
	
	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;
	
	@Override
	public void clearAllCache() {
		
	}

}
