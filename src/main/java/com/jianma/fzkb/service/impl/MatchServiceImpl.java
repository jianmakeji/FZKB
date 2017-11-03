package com.jianma.fzkb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jianma.fzkb.dao.MatchDao;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.service.MatchService;
import com.jianma.fzkb.util.ResponseCodeUtil;

@Repository
@Transactional
@Component
@Qualifier(value = "brandServiceImpl")
public class MatchServiceImpl implements MatchService {

	@Autowired
	@Qualifier("matchDaoImpl")
	private MatchDao matchDaoImpl;
	
	@Override
	public int createMatch(Match match) {
		try{
			matchDaoImpl.createMatch(match);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		}
		catch(Exception e){
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int updateMatch(Match match) {
		try{
			matchDaoImpl.updateMaterial(match);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		}
		catch(Exception e){
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int deleteMatch(int id) {
		try{
			matchDaoImpl.deleteMaterial(id);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		}
		catch(Exception e){
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public MatchTableModel getMatchByPage(int offset, int limit) {

		MatchTableModel matchTableModel = new MatchTableModel();
		matchTableModel.setCount(matchDaoImpl.getCountMatch());
		matchTableModel.setList(matchDaoImpl.getMatchByPage(offset, limit));
		return matchTableModel;
	}

	@Override
	public int getCountMatch() {

		return matchDaoImpl.getCountMatch();
	}

	@Override
	public Optional<Match> getDataByMatchId(int id) {

		return matchDaoImpl.getDataByMatchId(id);
	}

}
