package com.jianma.fzkb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.model.Material;
import com.jianma.fzkb.model.MaterialTableModel;
import com.jianma.fzkb.service.MaterialService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MaterialTest {
	
	@Autowired
	@Qualifier(value = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	@Qualifier(value="materialServiceImpl")
	private MaterialService materialServiceImpl;
	
	@Autowired
	@Qualifier("materialCacheImpl")
	private MaterialCache materialCacheImpl;
	
	//@Test
	public void MaterialRedisTest(){
		Material material = new Material();
		material.setCategoryName("艺术品");
		material.setName("材料1");
		material.setStyle1("2.0");
		material.setStyle2("1.0");
		material.setStyle3("-2.5");
		material.setCreateTime(new Date());
		material.setNumber("9635358695");
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("建筑");
		material.setName("材料2");
		material.setStyle1("0.5");
		material.setStyle2("2.5");
		material.setStyle3("-1.5");
		material.setNumber("7896542356");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("动物");
		material.setName("材料3");
		material.setStyle1("-2.0");
		material.setStyle2("2.5");
		material.setStyle3("-1.5");
		material.setNumber("2365896587");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("植物");
		material.setName("材料4");
		material.setStyle1("-2.0");
		material.setStyle2("1.0");
		material.setNumber("6547892351");
		material.setStyle3("2.5");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("风景");
		material.setName("材料5");
		material.setStyle1("-1.0");
		material.setStyle2("3.0");
		material.setStyle3("-1.5");
		material.setNumber("79865955632");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
	}
	
	//@Test
	public void updateMaterial(){
		Material material = new Material();
		material.setId(13);
		material.setCategoryName("艺术品");
		material.setName("东方艺术");
		material.setStyle1("-1.0");
		material.setStyle2("-3.0");
		material.setStyle3("3");
		material.setCreateTime(new Date());
		materialServiceImpl.updateMaterial(material);
	}
	
	//@Test
	public void testHashOps(){
		Material material = materialCacheImpl.getMaterialById(13);
		System.out.println("===========:"+material.getId());
		/*
		Map<Object,Object> map = redisTemplate.opsForHash().entries("material:data:13");
		int size = map.size();
		System.out.println("size:" + size);
		
		map.forEach((k,v)->{
			System.out.println(k+"  :  "+v);
		});*/
	}
	
	//@Test
	public void deleteMaterial(){
		materialServiceImpl.deleteMaterial(11);
	}
	
	@Test
	public void loadMaterialTest(){
		/*
		Map<String,String> map = new HashMap<>();
		map.put("category", "动物,风景");
		map.put("style1", "-1.0,1.0,3.0");
		map.put("style2", "1.0,2.0,1.5,3.0");
		map.put("style3", "-0.5,-1.5,-2.5");
		
		MaterialTableModel materialTableModel = materialServiceImpl.getMaterialPageByCondition(0, 10, map);
		System.out.println(materialTableModel.getCount());
		
		List<Material> list = materialTableModel.getList();
		
		list.stream().forEach((material)->{
			System.out.println(material.getId() + " " +material.getName() + " " +material.getCategoryName() + "  " + material.getStyle1() + "  " + material.getStyle2() + "  " + material.getStyle3());
		});
		*/
		
		Map<String,String> map = new HashMap<>();
		map.put("category", "动物,风景");
		map.put("style1", "");
		map.put("style2", "");
		map.put("style3", "");
		
		MaterialTableModel materialTableModel = materialServiceImpl.getMaterialPageByCondition(0, 10, map);
		System.out.println(materialTableModel.getCount());
		
		List<Material> list = materialTableModel.getList();
		
		list.stream().forEach((material)->{
			System.out.println(material.getId() + " " +material.getName() + " " +material.getCategoryName() + "  " + material.getStyle1() + "  " + material.getStyle2() + "  " + material.getStyle3());
		});
	}
}
