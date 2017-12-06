package com.jianma.fzkb.cache.redis;

import java.util.Map;

import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;

public interface MatchCache {

	public void addMatch(Match match, Map<String,Material> map);
	
	public void deleteMatch(int id, Map<String,Material> map);
	
	public void updateMatch(Match match, Map<String,Material> map);
	
	//根据条件筛选
	public MatchTableModel getMatchPageByCondition(int offset, int limit, Map<String,String> map);
}
