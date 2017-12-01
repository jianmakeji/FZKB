package com.jianma.fzkb.cache.redis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
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

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOperations;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;
	
	@Resource(name = "redisTemplate")
	private SetOperations<String,String> setOps;
	
	private final HashMapper<Material, String, String> mapper =
    	     new DecoratingStringHashMapper<Material>(new BeanUtilsHashMapper<Material>(Material.class));
    
    public void writeHash(String key, Material material) {
      Map<String, String> mappedHash = mapper.toHash(material);
      hashOperations.putAll(key, mappedHash);
    }

    public Material loadHash(String key) {
      Map<String, String> loadedHash = hashOperations.entries(key);
      return (Material) mapper.fromHash(loadedHash);
    }
    
	@Override
	public void addMaterial(Material material) {
		this.writeHash(RedisVariableUtil.MATERIAL_DATA_HASH+":"+material.getId().toString(), material);
		listOps.remove(RedisVariableUtil.MATERIAL_ID_LIST, 0, material.getId().toString());
		listOps.leftPush(RedisVariableUtil.MATERIAL_ID_LIST, material.getId().toString());
		setOps.add(RedisVariableUtil.categoryMap.get(material.getCategoryName()), material.getId().toString());
		setOps.add("s1:"+material.getStyle1(), material.getId().toString());
		setOps.add("s2:"+material.getStyle2(), material.getId().toString());
		setOps.add("s3:"+material.getStyle3(), material.getId().toString());
	}

	@Override
	public void deleteMaterial(int id) {
		Material material = this.loadHash(RedisVariableUtil.MATERIAL_DATA_HASH+":"+id);
		if (material != null){
			listOps.remove(RedisVariableUtil.MATERIAL_ID_LIST, 0, material.getId().toString());
			setOps.remove(RedisVariableUtil.categoryMap.get(material.getCategoryName()), material.getId().toString());
			setOps.remove("s1:"+material.getStyle1(), material.getId().toString());
			setOps.remove("s2:"+material.getStyle2(), material.getId().toString());
			setOps.remove("s3:"+material.getStyle3(), material.getId().toString());
			redisTemplate.delete(RedisVariableUtil.MATERIAL_DATA_HASH+":"+material.getId().toString());
		}
	}

	@Override
	public void updateMaterial(Material material) {
		//先删除
		this.deleteMaterial(material.getId());
		//再写入
		this.addMaterial(material);
	}

	@Override
	public List<Material> getMaterialByCondition(int userId, int limit, int offset) {
		List<String> idList = listOps.range(RedisVariableUtil.MATERIAL_ID_LIST, offset, offset+limit);
		List<Material> materialList = new ArrayList<>();
		for (String id : idList){
			materialList.add(this.loadHash(RedisVariableUtil.MATERIAL_DATA_HASH + ":" + id));
		}
		return materialList;
	}

	@Override
	public int getCountMaterial(int userId) {
		return listOps.size(RedisVariableUtil.MATERIAL_ID_LIST).intValue();
	}

	@Override
	public Material getMaterialById(int id) {
		// TODO Auto-generated method stub
		return this.loadHash(RedisVariableUtil.MATERIAL_DATA_HASH+":"+id);
	}

}
