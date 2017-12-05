package com.jianma.fzkb.cache.redis.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "matchCacheImpl")
public class MatchCacheImpl implements MatchCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	private final HashMapper<Match, String, String> mapper = new DecoratingStringHashMapper<Match>(
			new BeanUtilsHashMapper<Match>(Match.class));

	public void writeHash(HashOperations<String, String, String> hashOperations, String key, Match match) {
		Map<String, String> mappedHash = mapper.toHash(match);
		hashOperations.putAll(key, mappedHash);
	}

	public Match loadHash(HashOperations<String, String, String> hashOperations, String key) {
		DateConverter dateConverter = new DateConverter(null);
		dateConverter.setPatterns(new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
		ConvertUtils.register(dateConverter, Date.class);
		Map<String, String> loadedHash = hashOperations.entries(key);
		return (Match) mapper.fromHash(loadedHash);
	}

	@Override
	public void addMatch(Match match, Map<String, Material> map) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				ListOperations<String, String> listOps = operations.opsForList();
				HashOperations<String, String, String> hashOperations = operations.opsForHash();
				writeHash(hashOperations, RedisVariableUtil.MATCH_DATA_HASH + ":" + match.getId().toString(), match);
				listOps.remove(RedisVariableUtil.MATCH_ID_LIST, 0, match.getId().toString());
				listOps.leftPush(RedisVariableUtil.MATCH_ID_LIST, match.getId().toString());

				SetOperations<String, String> setOps = operations.opsForSet();

				map.forEach((key, material) -> {
					setOps.add(RedisVariableUtil.m_categoryMap.get(material.getCategoryName()),
							match.getId().toString());
					setOps.add("ms1:" + material.getStyle1(), match.getId().toString());
					setOps.add("ms2:" + material.getStyle2(), match.getId().toString());
					setOps.add("ms3:" + material.getStyle3(), match.getId().toString());
				});

				return operations.exec();
			}
		});
	}

	@Override
	public void deleteMatch(int id, Map<String, Material> map) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				HashOperations<String, String, String> hashOperations = operations.opsForHash();
				Match match = loadHash(hashOperations, RedisVariableUtil.MATERIAL_DATA_HASH + ":" + id);
				if (match != null) {
					ListOperations<String, String> listOps = operations.opsForList();
					listOps.remove(RedisVariableUtil.MATCH_ID_LIST, 0, match.getId().toString());
					SetOperations<String, String> setOps = operations.opsForSet();

					map.forEach((key, material) -> {
						setOps.remove(RedisVariableUtil.m_categoryMap.get(material.getCategoryName()),
								match.getId().toString());
						setOps.remove("ms1:" + material.getStyle1(), match.getId().toString());
						setOps.remove("ms2:" + material.getStyle2(), match.getId().toString());
						setOps.remove("ms3:" + material.getStyle3(), match.getId().toString());
					});

					redisTemplate.delete(RedisVariableUtil.MATERIAL_DATA_HASH + ":" + match.getId().toString());
				}
				return operations.exec();
			}
		});

	}

	@Override
	public void updateMatch(Match match, Map<String, Material> map) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				HashOperations<String, String, String> hashOperations = operations.opsForHash();
				SetOperations<String, String> setOps = operations.opsForSet();
				Match matchObj = loadHash(hashOperations, RedisVariableUtil.MATERIAL_DATA_HASH + ":" + match.getId());
				if (matchObj != null) {
					ListOperations<String, String> listOps = operations.opsForList();
					listOps.remove(RedisVariableUtil.MATCH_ID_LIST, 0, matchObj.getId().toString());
					map.forEach((key, material) -> {
						setOps.remove(RedisVariableUtil.m_categoryMap.get(material.getCategoryName()),
								matchObj.getId().toString());
						setOps.remove("ms1:" + material.getStyle1(), matchObj.getId().toString());
						setOps.remove("ms2:" + material.getStyle2(), matchObj.getId().toString());
						setOps.remove("ms3:" + material.getStyle3(), matchObj.getId().toString());
					});
					redisTemplate.delete(RedisVariableUtil.MATERIAL_DATA_HASH + ":" + matchObj.getId().toString());
				}

				writeHash(hashOperations, RedisVariableUtil.MATCH_DATA_HASH + ":" + match.getId().toString(), match);
				ListOperations<String, String> listOps = operations.opsForList();
				listOps.remove(RedisVariableUtil.MATCH_ID_LIST, 0, match.getId().toString());
				listOps.leftPush(RedisVariableUtil.MATCH_ID_LIST, match.getId().toString());

				map.forEach((key, material) -> {
					setOps.add(RedisVariableUtil.m_categoryMap.get(material.getCategoryName()),
							match.getId().toString());
					setOps.add("ms1:" + material.getStyle1(), match.getId().toString());
					setOps.add("ms2:" + material.getStyle2(), match.getId().toString());
					setOps.add("ms3:" + material.getStyle3(), match.getId().toString());
				});

				return operations.exec();
			}
		});
	}

	@Override
	public void getMatchByCondition(int userId, int limit, int offset) {

	}

	@Override
	public void getCountMatch(int userId) {

	}

}
