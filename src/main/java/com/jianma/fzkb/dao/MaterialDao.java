package com.jianma.fzkb.dao;

import java.util.List;
import java.util.Optional;

import com.jianma.fzkb.model.Material;

public interface MaterialDao {
	
	public void createMaterial(Material material);
	
	public void updateMaterial(Material material);
	
	public void deleteMaterial(int id);
	
	public List<Material> getMaterialByPage(int offset, int limit);
		
	public int getCountMaterial();
	
	public Optional<Material> getDataByMaterialId(int id);
	
	public List<Material> getAllMaterial();
}
