package com.jianma.fzkb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			e.printStackTrace();
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int updateMaterial(Material material) {
		try{
			materialDaoImpl.updateMaterial(material);
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
		materialTableModel.setCount(materialCacheImpl.getCountMaterial(0));
		materialTableModel.setList(materialCacheImpl.getMaterialByCondition(0, limit, offset));
		return materialTableModel;
	}

	@Override
	public int getCountMaterial() {
		return materialCacheImpl.getCountMaterial(0);
	}

	@Override
	public Optional<Material> getDataByMaterialId(int id) {
		Material material = materialCacheImpl.getMaterialById(id);
		if (material == null){
			return materialDaoImpl.getDataByMaterialId(id);
		}
		else{
			return Optional.ofNullable(material);
		}
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

	@Override
	public MaterialTableModel getMaterialPageByCondition(int offset, int limit, Map<String, String> map) {
		return materialCacheImpl.getMaterialPageByCondition(offset, limit, map);
	}

	@Override
	public MaterialTableModel getMaterialPageByNumber(int offset, int limit, String number) {
		MaterialTableModel materialTableModel = new MaterialTableModel();
		materialTableModel.setCount(materialDaoImpl.getMaterialCountByNumber(number));
		materialTableModel.setList(materialDaoImpl.getMaterialPageByNumber(offset, limit, number));
		return materialTableModel;
	}

	@Override
	public List<Material> getAllMaterial() {
		return materialDaoImpl.getAllMaterial();
	}

	@Override
	public MaterialTableModel getMaterialPageByUserId(int offset, int limit, int userId) {
		MaterialTableModel materialTableModel = new MaterialTableModel();
		materialTableModel.setCount(materialDaoImpl.getMaterialCountByUserId(userId));
		materialTableModel.setList(materialDaoImpl.getMaterialPageByUserId(offset, limit, userId));
		return materialTableModel;
	}

	@Override
	public Map<String, Material> getMaterialByIds(int uwId, int gcId, int trId) {
		Map<String, Material> map = new HashMap<String,Material>();
		map.put("underwear", materialDaoImpl.getDataByMaterialId(uwId).get());
		map.put("greatcoat", materialDaoImpl.getDataByMaterialId(gcId).get());
		map.put("trouser", materialDaoImpl.getDataByMaterialId(trId).get());
		return map;
	}

}
