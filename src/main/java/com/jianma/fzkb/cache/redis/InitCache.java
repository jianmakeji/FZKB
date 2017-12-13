package com.jianma.fzkb.cache.redis;

public interface InitCache {

	public boolean clearAllCache();
	
	public void initMaterial();
	
	public void initMatch();
}
