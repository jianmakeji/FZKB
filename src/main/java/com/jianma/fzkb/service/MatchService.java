package com.jianma.fzkb.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.MaterialTableModel;

public interface MatchService {

	public int createMatch(Match match);
	
	public int updateMatch(Match match);
	
	public int deleteMatch(int id);
	
	public MatchTableModel getMatchByPage(int offset, int limit);
		
	public int getCountMatch();
	
	public Optional<Match> getDataByMatchId(int id);
	
	//根据条件筛选
	public MatchTableModel getMatchPageByCondition(int offset, int limit, Map<String,String> map);
	
	public List<Match> getMatchPageByUserId(int offset, int limit,int userId);
	
	public int getCountMatchByUserId(int userId);
}
