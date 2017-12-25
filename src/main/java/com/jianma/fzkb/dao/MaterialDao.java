package com.jianma.fzkb.dao;

import java.util.List;
import java.util.Optional;

import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;

public interface MaterialDao {
	
	public int createMaterial(Material material);
	
	public void updateMaterial(Material material);
	
	public void deleteMaterial(int id);
	
	public List<Material> getMaterialByPage(int offset, int limit);
		
	public int getCountMaterial();
	
	public Optional<Material> getDataByMaterialId(int id);
	
	public List<Material> getAllMaterial();
	
	//根据编号查询
	public List<Material> getMaterialPageByNumber(int offset, int limit, String number);
	
	public int getMaterialCountByNumber(String number);
	
	public List<Material> getMaterialPageByUserId(int offset, int limit, int userId);
	
	public int getMaterialCountByUserId(int userId);
}
