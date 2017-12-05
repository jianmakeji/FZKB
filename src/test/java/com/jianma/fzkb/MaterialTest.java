package com.jianma.fzkb;

import java.util.Date;
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
		material.setMasterImage("http://");
		material.setName("材料");
		material.setThumb("http://");
		material.setStyle1("2.0");
		material.setStyle2("1.0");
		material.setStyle3("-2.5");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("建筑");
		material.setMasterImage("http://");
		material.setName("材料");
		material.setThumb("http://");
		material.setStyle1("1.0");
		material.setStyle2("2.0");
		material.setStyle3("-1.5");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("动物");
		material.setMasterImage("http://");
		material.setName("材料");
		material.setThumb("http://");
		material.setStyle1("3.0");
		material.setStyle2("2.5");
		material.setStyle3("-0.5");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("植物");
		material.setMasterImage("http://");
		material.setName("材料");
		material.setThumb("http://");
		material.setStyle1("-2.0");
		material.setStyle2("2.0");
		material.setStyle3("2.5");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
		
		material = new Material();
		material.setCategoryName("风景");
		material.setMasterImage("http://");
		material.setName("材料");
		material.setThumb("http://");
		material.setStyle1("2.0");
		material.setStyle2("1.0");
		material.setStyle3("-0.5");
		material.setCreateTime(new Date());
		materialServiceImpl.createMaterial(material);
	}
	
	@Test
	public void updateMaterial(){
		Material material = new Material();
		material.setId(13);
		material.setCategoryName("艺术品");
		material.setMasterImage("http://www");
		material.setName("东方艺术");
		material.setThumb("http://xxx");
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
	
	public void deleteMaterial(){
		
	}
	
	//@Test
	public void loadMaterialTest(){
		
	}
}
