package com.jianma.fzkb.service;

import java.util.Optional;

import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;

public interface MatchService {

	public int createMatch(Match match);
	
	public int updateMatch(Match match);
	
	public int deleteMatch(int id);
	
	public MatchTableModel getMatchByPage(int offset, int limit);
		
	public int getCountMatch();
	
	public Optional<Match> getDataByMatchId(int id);
}
