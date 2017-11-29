package com.jianma.fzkb.cache.redis;

import com.jianma.fzkb.model.Material;

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
	public void getMaterialByCondition(int userId, int limit, int offset);
	
	/**
	 * userId=0 查找全部的，userId!=0 按照userId查找
	 * @param userId
	 */
	public void getCountMaterial(int userId);
}
