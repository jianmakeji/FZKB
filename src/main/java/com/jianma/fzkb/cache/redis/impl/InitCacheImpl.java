package com.jianma.fzkb.cache.redis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.cache.redis.InitCache;
import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.dao.MatchDao;
import com.jianma.fzkb.dao.MaterialDao;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.service.MatchService;
import com.jianma.fzkb.service.MaterialService;

@Repository
@Component
@Qualifier(value = "initCacheImpl")
public class InitCacheImpl implements InitCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
		
	@Autowired
	@Qualifier(value = "matchServiceImpl")
	private MatchService matchServiceImpl;
	
	@Autowired
	@Qualifier("materialServiceImpl")
	private MaterialService materialServiceImpl;
		
	@Autowired
	@Qualifier("matchCacheImpl")
	private MatchCache matchCacheImpl;

	@Autowired
	@Qualifier("materialCacheImpl")
	private MaterialCache materialCacheImpl;
	
	@Override
	public boolean clearAllCache() {
		 return redisTemplate.execute(new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
	}

	@Override
	public void initMaterial() {
		List<Material> list = materialServiceImpl.getAllMaterial();
		for (Material material : list){
			materialCacheImpl.addMaterial(material);
		}
		
	}

	@Override
	public void initMatch() {
		List<Match> list = matchServiceImpl.getMatchByPage(0, Integer.MAX_VALUE);
		for (Match match : list){
			Map<String, Material> map = new HashMap<String, Material>();
			map.put("underwear", materialCacheImpl.getMaterialById(match.getUwId()));
			map.put("greatcoat", materialCacheImpl.getMaterialById(match.getGcId()));
			map.put("trouser", materialCacheImpl.getMaterialById(match.getTrId()));
			
			matchCacheImpl.addMatch(match, map);
		}
	}

}
