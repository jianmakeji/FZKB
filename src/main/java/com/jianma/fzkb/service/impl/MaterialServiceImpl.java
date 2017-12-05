package com.jianma.fzkb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.dao.MaterialDao;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;
import com.jianma.fzkb.service.MaterialService;
import com.jianma.fzkb.util.ResponseCodeUtil;

@Repository
@Transactional
@Component
@Qualifier(value = "materialServiceImpl")
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	@Qualifier("materialDaoImpl")
	private MaterialDao materialDaoImpl;
	
	@Autowired
	@Qualifier("materialCacheImpl")
	private MaterialCache materialCacheImpl;
	
	@Override
	public int createMaterial(Material material) {
		try{
			int id = materialDaoImpl.createMaterial(material);
			material.setId(id);
			materialCacheImpl.addMaterial(material);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		}
		catch(Exception e){
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int updateMaterial(Material material) {
		try{
			//materialDaoImpl.updateMaterial(material);
			materialCacheImpl.updateMaterial(material);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		}
		catch(Exception e){
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int deleteMaterial(int id) {
		try{
			materialDaoImpl.deleteMaterial(id);
			materialCacheImpl.deleteMaterial(id);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		}
		catch(Exception e){
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public MaterialTableModel getMaterialByPage(int offset, int limit) {
		MaterialTableModel materialTableModel = new MaterialTableModel();
		materialTableModel.setCount(materialDaoImpl.getCountMaterial());
		materialTableModel.setList(materialDaoImpl.getMaterialByPage(offset, limit));
		return materialTableModel;
	}

	@Override
	public int getCountMaterial() {
		return materialDaoImpl.getCountMaterial();
	}

	@Override
	public Optional<Material> getDataByMaterialId(int id) {
		return materialDaoImpl.getDataByMaterialId(id);
	}

	@Override
	public List<Material> getMaterialByCount(int count){
		List<Material> list = materialDaoImpl.getAllMaterial();
		List<Material> resultList = new ArrayList<>();
		Random ra =new Random();
		for (int i = 0; i < count; i++){
			resultList.add(list.get(ra.nextInt(list.size())));
		}
		return resultList;
	}

}
