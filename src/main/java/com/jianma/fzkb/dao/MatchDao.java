package com.jianma.fzkb.dao;

import java.util.List;
import java.util.Optional;

import com.jianma.fzkb.model.Match;

public interface MatchDao {

	public void createMatch(Match match);
	
	public void updateMatch(Match match);
	
	public void deleteMatch(int id);
		
	public List<Match> getMatchPageByUserId(int offset, int limit,int userId);
	
	public int getCountMatchByUserId(int userId);
	
	public Optional<Match> getDataByMatchId(int id);
	
	public List<Match> getMatchBySearchKeyword(int offset, int limit, int userId, String keyword);
	
	public int getCountMatchBySearchKeyword(int userId,String keyword);
}
