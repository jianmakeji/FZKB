package com.jianma.fzkb.cache.redis.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.codec.digest.DigestUtils;
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

import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;
import com.jianma.fzkb.util.RedisVariableUtil;

@Repository
@Component
@Qualifier(value = "materialCacheImpl")
public class MaterialCacheImpl implements MaterialCache {

	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	private final HashMapper<Material, String, String> mapper =
    	     new DecoratingStringHashMapper<Material>(new BeanUtilsHashMapper<Material>(Material.class));
    
	private String cacheKey;
	
    public void writeHash(HashOperations<String, String, String> hashOperations, String key, Material material) {
      Map<String, String> mappedHash = mapper.toHash(material);
      mappedHash.remove("class");
      hashOperations.putAll(key, mappedHash);
    }

    public Material loadHash(HashOperations<String, String, String> hashOperations,String key) {
      DateConverter dateConverter = new DateConverter(null);
      dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy/MM/dd"});
      ConvertUtils.register(dateConverter, Date.class);      
      Map<String, String> loadedHash = hashOperations.entries(key);
      Material material = (Material) mapper.fromHash(loadedHash);
      return material;
    }
    
	@Override
	public void addMaterial(Material material) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			  public List<Object> execute(RedisOperations operations) throws DataAccessException {
			    operations.multi();
			    HashOperations<String, String, String> hashOperations = operations.opsForHash();
			    writeHash(hashOperations, RedisVariableUtil.MATERIAL_DATA_HASH+":"+material.getId().toString(), material);
			    
			    ListOperations<String, String> listOps = operations.opsForList();
				listOps.remove(RedisVariableUtil.MATERIAL_ID_LIST, 0, material.getId().toString());
				listOps.leftPush(RedisVariableUtil.MATERIAL_ID_LIST, material.getId().toString());
				
				SetOperations<String,String> setOps = operations.opsForSet();
				setOps.add(RedisVariableUtil.categoryMap.get(material.getCategoryName()), material.getId().toString());
				setOps.add("s1:"+material.getStyle1(), material.getId().toString());
				setOps.add("s2:"+material.getStyle2(), material.getId().toString());
				setOps.add("s3:"+material.getStyle3(), material.getId().toString());
			    return operations.exec();
			  }
		});
		
		
	}

	@Override
	public void deleteMaterial(int id) {
		Material material = getMaterialById(id);
		
		Material material = getMaterialById(id);
		
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			  public List<Object> execute(RedisOperations operations) throws DataAccessException {
			    operations.multi();
			    HashOperations<String, String, String> hashOperations = operations.opsForHash();
			    
				if (material != null){
					ListOperations<String, String> listOps = operations.opsForList();
					listOps.remove(RedisVariableUtil.MATERIAL_ID_LIST, 0, material.getId().toString());
					
					SetOperations<String,String> setOps = operations.opsForSet();
					setOps.remove(RedisVariableUtil.categoryMap.get(material.getCategoryName()), material.getId().toString());
					setOps.remove("s1:"+material.getStyle1(), material.getId().toString());
					setOps.remove("s2:"+material.getStyle2(), material.getId().toString());
					setOps.remove("s3:"+material.getStyle3(), material.getId().toString());
					operations.delete(RedisVariableUtil.MATERIAL_DATA_HASH+":"+material.getId().toString());
					
				}
			    return operations.exec();
			  }
		});
	}

	@Override
	public void updateMaterial(Material materialObj) {

	    Material material = getMaterialById(materialObj.getId());

	    if (material != null){
	    	redisTemplate.execute(new SessionCallback<List<Object>>() {
				  public List<Object> execute(RedisOperations operations) throws DataAccessException {
				    operations.multi();
				    HashOperations<String, String, String> hashOperations = operations.opsForHash();
				    ListOperations<String, String> listOps = operations.opsForList();
				    SetOperations<String,String> setOps = operations.opsForSet();
				   			    
					if (material != null){
						listOps.remove(RedisVariableUtil.MATERIAL_ID_LIST, 0, material.getId().toString());
						setOps.remove(RedisVariableUtil.categoryMap.get(material.getCategoryName()), material.getId().toString());
						setOps.remove("s1:"+material.getStyle1(), material.getId().toString());
						setOps.remove("s2:"+material.getStyle2(), material.getId().toString());
						setOps.remove("s3:"+material.getStyle3(), material.getId().toString());
						operations.delete(RedisVariableUtil.MATERIAL_DATA_HASH+":"+material.getId().toString());
					}
					
					writeHash(hashOperations, RedisVariableUtil.MATERIAL_DATA_HASH+":"+materialObj.getId().toString(), materialObj);
				    
					listOps.remove(RedisVariableUtil.MATERIAL_ID_LIST, 0, materialObj.getId().toString());
					listOps.leftPush(RedisVariableUtil.MATERIAL_ID_LIST, materialObj.getId().toString());
					
					setOps.add(RedisVariableUtil.categoryMap.get(materialObj.getCategoryName()), materialObj.getId().toString());
					setOps.add("s1:"+material.getStyle1(), materialObj.getId().toString());
					setOps.add("s2:"+material.getStyle2(), materialObj.getId().toString());
					setOps.add("s3:"+material.getStyle3(), materialObj.getId().toString());
					
				    return operations.exec();
				  }
			});
	    }
	}

	@Override
	public List<Material> getMaterialByCondition(int userId, int limit, int offset) {
		
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		List<String> idList = listOps.range(RedisVariableUtil.MATERIAL_ID_LIST, offset, offset+limit);
		List<Material> materialList = new ArrayList<>();
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		for (String id : idList){
			materialList.add(loadHash(hashOperations,RedisVariableUtil.MATERIAL_DATA_HASH + ":" + id));
		}
		return materialList;
	}

	@Override
	public int getCountMaterial(int userId) {
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		return listOps.size(RedisVariableUtil.MATERIAL_ID_LIST).intValue();
	}

	@Override
	public Material getMaterialById(int id) {
		// TODO Auto-generated method stub
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		return this.loadHash(hashOperations,RedisVariableUtil.MATERIAL_DATA_HASH + ":" + id);
	}

	@Override
	public MaterialTableModel getMaterialPageByCondition(int offset, int limit, Map<String, String> map) {

		String[] categoryArr = map.get("category").split(",");
		List<String> category = new ArrayList<>();
		for (String s : categoryArr){
			category.add(RedisVariableUtil.categoryMap.get(s));
		}
		
		String[] style1Arr = map.get("style1").split(",");
		List<String> style1 = new ArrayList<>();
		for (String s : style1Arr){
			style1.add("s1:"+s);
		}
		
		String[] style2Arr = map.get("style2").split(",");
		List<String> style2 = new ArrayList<>();
		for (String s : style2Arr){
			style2.add("s2:"+s);
		}
		
		String[] style3Arr = map.get("style3").split(",");
		List<String> style3 = new ArrayList<>();
		for (String s : style3Arr){
			style3.add("s3:"+s);
		}
		
		return redisTemplate.execute(new RedisCallback<MaterialTableModel>() {

			@Override
			public  MaterialTableModel doInRedis(RedisConnection connection) throws DataAccessException {

				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				
				StringBuilder tagListStr = new StringBuilder();
				map.forEach((k, v) -> {
					tagListStr.append(v);
				});
				cacheKey = DigestUtils.md5Hex(tagListStr.toString());

				if (!connection.exists(ser.serialize(cacheKey))) {

					List<String> calculateKeys = new ArrayList<>();
					
					String calculateCategoryResult = RedisVariableUtil.CATEGORY_PREFIX + RedisVariableUtil.DIVISION_CHAR + cacheKey;
					
					if (category.size() > 0) {
						redisTemplate.opsForSet().unionAndStore(category.get(0), category, calculateCategoryResult);
						calculateKeys.add(calculateCategoryResult);
					}

					String calculateStyle1Result = RedisVariableUtil.STYLE1_PREFIX + RedisVariableUtil.DIVISION_CHAR + cacheKey;
					
					if (style1.size() > 0) {
						redisTemplate.opsForSet().unionAndStore(style1.get(0), style1, calculateStyle1Result);
						calculateKeys.add(calculateStyle1Result);
					}
					
					String calculateStyle2Result = RedisVariableUtil.STYLE2_PREFIX + RedisVariableUtil.DIVISION_CHAR + cacheKey;
					
					if (style2.size() > 0) {
						redisTemplate.opsForSet().unionAndStore(style2.get(0), style2, calculateStyle2Result);
						calculateKeys.add(calculateStyle2Result);
					}
					
					String calculateStyle3Result = RedisVariableUtil.STYLE3_PREFIX + RedisVariableUtil.DIVISION_CHAR + cacheKey;
					
					if (style3.size() > 0) {
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
				
				MaterialTableModel materialTableModel = new MaterialTableModel();
				List<Material> list = new ArrayList<>();
				long length = connection.lLen(ser.serialize(cacheKey));
				materialTableModel.setCount(length);
				
				List<byte[]> keyList = null;
				if ((offset + limit) < length)
				{
					keyList = connection.lRange(ser.serialize(cacheKey), offset, offset + limit);
				}
				else{
					keyList = connection.lRange(ser.serialize(cacheKey), offset, length);
				}
				
				for (byte[] key : keyList){
					list.add(getMaterialById(Integer.parseInt(ser.deserialize(key))));
				}

				return materialTableModel;
			}
		});
	}

}
