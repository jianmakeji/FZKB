package com.jianma.fzkb.cache.redis.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.model.Match;

@Repository
@Component
@Qualifier(value = "matchCacheImpl")
public class MatchCacheImpl implements MatchCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	HashOperations<String, String, String> hashOperations;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;
	
	@Override
	public void addMatch(Match match) {
		
	}

	@Override
	public void deleteMatch(int id) {
		
	}

	@Override
	public void updateMatch(Match match) {
		
	}

	@Override
	public void getMatchByCondition(int userId, int limit, int offset) {
		
	}

	@Override
	public void getCountMatch(int userId) {
		
	}

}
