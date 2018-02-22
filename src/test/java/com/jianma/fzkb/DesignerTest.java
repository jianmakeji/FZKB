package com.jianma.fzkb;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.DesignerTableModel;
import com.jianma.fzkb.service.DesignerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class DesignerTest {

	@Autowired
	@Qualifier(value="designerServiceImpl")
	private DesignerService designerServiceImpl;
	
	@Test
	public void addDesigner(){
		Designer designer = new Designer();
		designer.setCreateTime(new Date());
		designer.setUsername("cidic@cidic.cn");
		designer.setRealname("cidic_hn");
		designer.setPassword("111111");
		designer.setRole(0);
		designerServiceImpl.createDesigner(designer);
	}
	
	//@Test
	public void updateDesigner(){
		Designer designer = new Designer();
		designer.setId(1);
		designer.setCreateTime(new Date());
		designer.setUsername("cidic@cidic.cn");
		designer.setRealname("cidic_china");
		designer.setIntroduce("hello cidic...");
		designer.setPassword("222222");
		designerServiceImpl.updateDesigner(designer);
	}
	
	//@Test
	public void deleteDesigner(){
		designerServiceImpl.deleteDesigner(3);
	}
	
	//@Test
	public void loadDesigner(){
		DesignerTableModel designerTableModel = designerServiceImpl.getDesignerByRealname("cidic", 0, 10);
		System.out.println(designerTableModel.getCount());
		
		DesignerTableModel designerTableModel2 = designerServiceImpl.getDesignerByPage(10, 10);
		System.out.println(designerTableModel2.getList().size());
		
		Optional<Designer> designerOpt = designerServiceImpl.getDesignerById(13);
		designerOpt.ifPresent((designer)->{
			System.out.println(designer.getRealname());
		});
	}
	
	//@Test
	public void checkAuth(){
		/*
		 Optional<Designer> designerOpt1 = designerServiceImpl.authorityCheck("cidic@188.vip.com", "111111");
		 designerOpt1.ifPresent((designer)->{
			 System.out.println(designer.getRealname());
		 });
		 
		 Optional<Designer> designerOpt2 = designerServiceImpl.authorityCheck("cidic@188.vip.com", "111111222");
		 if (designerOpt2.isPresent()){
			System.out.println("check success"); 
		 }
		 else{
			System.out.println("check failure"); 
		 }
		 */
	}
}
