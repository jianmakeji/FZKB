package com.jianma.fzkb.cache.redis.impl;

import java.util.ArrayList;
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

import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.model.Material;
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
		return this.loadHash(hashOperations,RedisVariableUtil.MATERIAL_DATA_HASH+":"+id);
	}

}
