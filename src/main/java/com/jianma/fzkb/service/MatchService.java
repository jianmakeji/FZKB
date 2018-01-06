package com.jianma.fzkb.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;

public interface MatchService {

	public int createMatch(Match match);
	
	public int updateMatch(Match match);
	
	public int deleteMatch(int id);
	
	public Optional<Match> getDataByMatchId(int id);
	
	//根据条件筛选
	public MatchTableModel getMatchPageByCondition(int offset, int limit, Map<String,String> map);
	
	public MatchTableModel getMatchPageByUserId(int offset, int limit,int userId);
	
	public List<Match> getMatchByPage(int offset, int limit, int userId);
	
	public MatchTableModel getMatchBySearchKeyword(int offset, int limit,int userId,String keyword);
}
