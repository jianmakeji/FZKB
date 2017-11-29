package com.jianma.fzkb.cache.redis;

import com.jianma.fzkb.model.Match;

public interface MatchCache {

	public void addMatch(Match match);
	
	public void deleteMatch(int id);
	
	public void updateMatch(Match match);
	
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
