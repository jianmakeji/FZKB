package com.jianma.fzkb.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;

public interface MaterialService {

	public int createMaterial(Material material);
	
	public int updateMaterial(Material material);
	
	public int deleteMaterial(int id);
	
	public MaterialTableModel getMaterialByPage(int offset, int limit);
		
	public int getCountMaterial();
	
	public Optional<Material> getDataByMaterialId(int id);
	
	public List<Material> getMaterialByCount(int count);
	
	//根据条件筛选
	public MaterialTableModel getMaterialPageByCondition(int offset, int limit, Map<String,String> map);
	
	//根据编号查询
	public MaterialTableModel getMaterialPageByNumber(int offset, int limit, String number);
	
	public List<Material> getAllMaterial();
	
}
