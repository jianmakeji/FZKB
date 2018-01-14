package com.jianma.fzkb.service;

import java.util.Optional;

import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.DesignerTableModel;

public interface DesignerService {

	public int createDesigner(Designer designer);
	
	public int updateDesigner(Designer designer);
	
	public int deleteDesigner(int id);
	
	public Optional<Designer> getDesignerById(int id);
	
	public Optional<Designer> findDesignerByUsername(String username);
	
	public DesignerTableModel getDesignerByPage(int offset, int limit);
	
	public DesignerTableModel getDesignerByRealname(String realname, int offset, int limit);
	
	public Optional<Designer> authorityCheck(String username, String password); 
	
	public int updatePwd(int designerId, String password);
}
