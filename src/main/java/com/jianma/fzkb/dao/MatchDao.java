package com.jianma.fzkb.dao;

import java.util.List;
import java.util.Optional;

import com.jianma.fzkb.model.Match;

public interface MatchDao {

	public void createMatch(Match match);
	
	public void updateMaterial(Match match);
	
	public void deleteMaterial(int id);
	
	public List<Match> getMatchByPage(int offset, int limit);
		
	public int getCountMatch();
	
	public Optional<Match> getDataByMatchId(int id);
	
}
