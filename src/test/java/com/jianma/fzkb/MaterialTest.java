package com.jianma.fzkb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.cache.redis.MaterialCache;
import com.jianma.fzkb.model.Material;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MaterialTest {

	@Autowired
	@Qualifier(value="materialCacheImpl")
	private MaterialCache materialCache;
	
	//@Test
	public void MaterialRedisTest(){
		Material material = new Material();
		material.setCategoryName("艺术品");
		material.setId(2);
		material.setMasterImage("http://");
		material.setName("材料");
		material.setThumb("http://");
		material.setStyle1("1.0");
		material.setStyle2("2.0");
		material.setStyle3("-1.5");
		materialCache.addMaterial(material);
	}
	
	@Test
	public void loadMaterialTest(){
		Material material = materialCache.getMaterialById(6);
		System.out.println(material.getCategoryName()+"   "+material.getName());
	}
}
