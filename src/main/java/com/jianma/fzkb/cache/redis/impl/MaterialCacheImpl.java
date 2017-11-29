package com.jianma.fzkb.cache.redis.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.model.Material;

@Repository
@Component
@Qualifier(value = "materialCacheImpl")
public class MaterialCacheImpl implements MaterialCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	HashOperations<String, String, String> hashOperations;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;
	
	
	@Override
	public void addMaterial(Material material) {
		
	}

	@Override
	public void deleteMaterial(int id) {
		
	}

	@Override
	public void updateMaterial(Material material) {
		
	}

	@Override
	public void getMaterialByCondition(int userId, int limit, int offset) {
		
	}

	@Override
	public void getCountMaterial(int userId) {
		
	}

}
