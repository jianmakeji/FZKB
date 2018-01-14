package com.jianma.fzkb.dao;

import java.util.List;
import java.util.Optional;

import com.jianma.fzkb.model.Designer;

public interface DesignerDao {
	
	public void createDesigner(Designer designer);
	
	public void updateDesigner(Designer designer);
	
	public void deleteDesigner(int id);
	
	public Optional<Designer> getDesignerById(int id);
	
	public Optional<Designer> findDesignerByUsername(String username);
	
	public List<Designer> getDesignerByPage(int offset, int limit);
	
	public int getCountDesigner();
	
	public List<Designer> getDesignerByRealname(String realname, int offset, int limit);
	
	public int getCountDesignerByRealname(String realname);
	
	public Optional<Designer> authorityCheck(String username, String password); 
	
	public void updatePwd(int designerId, String password);
}
