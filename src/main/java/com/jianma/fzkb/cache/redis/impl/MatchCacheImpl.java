package com.jianma.fzkb.cache.redis.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.jianma.fzkb.cache.redis.MatchCache;
import com.jianma.fzkb.model.Match;
import com.jianma.fzkb.model.MatchTableModel;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;
import com.jianma.fzkb.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "matchCacheImpl")
public class MatchCacheImpl implements MatchCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	private String cacheKey;

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
	public MatchTableModel getMatchPageByCondition(int offset, int limit, Map<String, String> map) {
		String[] categoryArr = map.get("category").split(",");
		List<String> category = new ArrayList<>();
		for (String s : categoryArr) {
			category.add(RedisVariableUtil.m_categoryMap.get(s));
		}

		String[] style1Arr = map.get("style1").split(",");
		List<String> style1 = new ArrayList<>();
		for (String s : style1Arr) {
			style1.add("ms1:" + s);
		}

		String[] style2Arr = map.get("style2").split(",");
		List<String> style2 = new ArrayList<>();
		for (String s : style2Arr) {
			style2.add("ms2:" + s);
		}

		String[] style3Arr = map.get("style3").split(",");
		List<String> style3 = new ArrayList<>();
		for (String s : style3Arr) {
			style3.add("ms3:" + s);
		}

		return redisTemplate.execute(new RedisCallback<MatchTableModel>() {

			@Override
			public MatchTableModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();

				StringBuilder tagListStr = new StringBuilder();
				map.forEach((k, v) -> {
					tagListStr.append(v);
				});
				cacheKey = DigestUtils.md5Hex("match" + tagListStr.toString());

				if (!connection.exists(ser.serialize(cacheKey))) {

					List<String> calculateKeys = new ArrayList<>();

					String calculateCategoryResult = RedisVariableUtil.M_CATEGORY_PREFIX + RedisVariableUtil.DIVISION_CHAR
							+ cacheKey;

					if (category.size() > 1) {
						redisTemplate.opsForSet().unionAndStore(category.get(0), category, calculateCategoryResult);
						calculateKeys.add(calculateCategoryResult);
					}

					String calculateStyle1Result = RedisVariableUtil.M_STYLE1_PREFIX + RedisVariableUtil.DIVISION_CHAR
							+ cacheKey;

					if (style1.size() > 1) {
						redisTemplate.opsForSet().unionAndStore(style1.get(0), style1, calculateStyle1Result);
						calculateKeys.add(calculateStyle1Result);
					}

					String calculateStyle2Result = RedisVariableUtil.M_STYLE2_PREFIX + RedisVariableUtil.DIVISION_CHAR
							+ cacheKey;

					if (style2.size() > 1) {
						redisTemplate.opsForSet().unionAndStore(style2.get(0), style2, calculateStyle2Result);
						calculateKeys.add(calculateStyle2Result);
					}

					String calculateStyle3Result = RedisVariableUtil.M_STYLE3_PREFIX + RedisVariableUtil.DIVISION_CHAR
							+ cacheKey;

					if (style3.size() > 1) {
						redisTemplate.opsForSet().unionAndStore(style3.get(0), style3, calculateStyle3Result);
						calculateKeys.add(calculateStyle3Result);
					}

					Set<String> result = redisTemplate.opsForSet().intersect(calculateKeys.get(0), calculateKeys);

					for (String data : result) {
						connection.lPush(ser.serialize(cacheKey), ser.serialize(data));
					}

					connection.expire(ser.serialize(cacheKey), 300);

					calculateKeys.forEach((s) -> {
						redisTemplate.delete(s);
					});
				}

				MatchTableModel matchTableModel = new MatchTableModel();
				List<Match> list = new ArrayList<>();
				long length = connection.lLen(ser.serialize(cacheKey));
				matchTableModel.setCount(length);

				List<byte[]> keyList = null;
				if ((offset + limit) < length) {
					keyList = connection.lRange(ser.serialize(cacheKey), offset, offset + limit);
				} else {
					keyList = connection.lRange(ser.serialize(cacheKey), offset, length);
				}

				for (byte[] key : keyList) {
					list.add(loadHash(redisTemplate.opsForHash(),ser.deserialize(key)));
				}

				return matchTableModel;
			}
		});
	}

}
