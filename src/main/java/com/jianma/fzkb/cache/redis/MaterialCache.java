package com.jianma.fzkb.cache.redis;

import java.util.List;
import java.util.Map;

import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;

public interface MaterialCache {

	public void addMaterial(Material material);
	
	public void deleteMaterial(int id);
	
	public void updateMaterial(Material material);
	
	/**
	 * userId=0 查找全部的，userId!=0 按照userId查找
	 * @param userId
	 * @param limit
	 * @param offset
	 */
	public List<Material> getMaterialByCondition(int userId, int limit, int offset);
	
	/**
	 * userId=0 查找全部的，userId!=0 按照userId查找
	 * @param userId
	 */
	public int getCountMaterial(int userId);
	
	public Material getMaterialById(int id);
	
	//根据条件筛选
	public MaterialTableModel getMaterialPageByCondition(int offset, int limit, Map<String,String> map);
}
