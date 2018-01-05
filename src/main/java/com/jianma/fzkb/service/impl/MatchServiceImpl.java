package com.jianma.fzkb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.dao.MatchDao;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.service.MatchService;
import com.jianma.fzkb.util.ResponseCodeUtil;

@Repository
@Transactional
@Component
@Qualifier(value = "matchServiceImpl")
public class MatchServiceImpl implements MatchService {

	@Autowired
	@Qualifier("matchDaoImpl")
	private MatchDao matchDaoImpl;

	@Autowired
	@Qualifier("matchCacheImpl")
	private MatchCache matchCacheImpl;

	@Autowired
	@Qualifier("materialCacheImpl")
	private MaterialCache materialCacheImpl;

	@Override
	public int createMatch(Match match) {
		try {
			matchDaoImpl.createMatch(match);
			Map<String, Material> map = new HashMap<String, Material>();
			map.put("underwear", materialCacheImpl.getMaterialById(match.getUwId()));
			map.put("greatcoat", materialCacheImpl.getMaterialById(match.getGcId()));
			map.put("trouser", materialCacheImpl.getMaterialById(match.getTrId()));
			matchCacheImpl.addMatch(match, map);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int updateMatch(Match match) {
		try {
			matchDaoImpl.updateMatch(match);
			Map<String, Material> map = new HashMap<String, Material>();
			map.put("underwear", materialCacheImpl.getMaterialById(match.getUwId()));
			map.put("greatcoat", materialCacheImpl.getMaterialById(match.getGcId()));
			map.put("trouser", materialCacheImpl.getMaterialById(match.getTrId()));
			
			matchCacheImpl.updateMatch(match, map);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int deleteMatch(int id) {
		try {
			Optional<Match> match = matchDaoImpl.getDataByMatchId(id);
			match.ifPresent((matchObj) -> {
				matchDaoImpl.deleteMatch(id);
				Map<String, Material> map = new HashMap<String, Material>();
				map.put("underwear", materialCacheImpl.getMaterialById(matchObj.getUwId()));
				map.put("greatcoat", materialCacheImpl.getMaterialById(matchObj.getGcId()));
				map.put("trouser", materialCacheImpl.getMaterialById(matchObj.getTrId()));
				matchCacheImpl.deleteMatch(id, map);
			});
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public Optional<Match> getDataByMatchId(int id) {

		return matchDaoImpl.getDataByMatchId(id);
	}

	@Override
	public MatchTableModel getMatchPageByCondition(int offset, int limit, Map<String, String> map) {
		
		return matchCacheImpl.getMatchPageByCondition(offset, limit, map);
	}

	@Override
	public MatchTableModel getMatchPageByUserId(int offset, int limit, int userId) {
		MatchTableModel matchTableModel = new MatchTableModel();
		List<Match> list = matchDaoImpl.getMatchPageByUserId(offset, limit, userId);
		int count = matchDaoImpl.getCountMatchByUserId(userId);
		matchTableModel.setList(list);
		matchTableModel.setCount(count);
		return matchTableModel;
	}

	@Override
	public List<Match> getMatchByPage(int offset, int limit) {
		return matchDaoImpl.getMatchPageByUserId(offset, limit, 0);
	}

	@Override
	public MatchTableModel getMatchBySearchKeyword(int offset, int limit, int userId, String keyword) {
		MatchTableModel matchTableModel = new MatchTableModel();
		List<Match> list = matchDaoImpl.getMatchBySearchKeyword(offset, limit, userId, keyword);
		int count = matchDaoImpl.getCountMatchBySearchKeyword(userId, keyword);
		matchTableModel.setList(list);
		matchTableModel.setCount(count);
		return matchTableModel;
	}

}
