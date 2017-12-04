package com.jianma.fzkb.cache.redis;

import java.util.Map;

import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.Material;

public interface MatchCache {

	public void addMatch(Match match, Map<String,Material> map);
	
	public void deleteMatch(int id, Map<String,Material> map);
	
	public void updateMatch(Match match, Map<String,Material> map);
	
	/**
	 * userId=0 查找全部的，userId!=0 按照userId查找
	 * @param userId
	 * @param limit
	 * @param offset
	 */
	public void getMatchByCondition(int userId, int limit, int offset);
	
	/**
	 * userId=0 查找全部的，userId!=0 按照userId查找
	 * @param userId
	 */
	public void getCountMatch(int userId);
}
