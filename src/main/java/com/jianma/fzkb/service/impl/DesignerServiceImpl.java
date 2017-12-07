package com.jianma.fzkb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jianma.fzkb.dao.DesignerDao;
import com.jianma.fzkb.model.Designer;
import com.jianma.fzkb.model.DesignerTableModel;
import com.jianma.fzkb.service.DesignerService;
import com.jianma.fzkb.util.PasswordHelper;
import com.jianma.fzkb.util.ResponseCodeUtil;

@Repository
@Transactional
@Component
@Qualifier(value = "designerServiceImpl")
public class DesignerServiceImpl implements DesignerService {

	@Autowired
	@Qualifier("designerDaoImpl")
	private DesignerDao designerDaoImpl;
	
	@Override
	public int createDesigner(Designer designer) {
		try {
			Optional<Designer> optDesigner = designerDaoImpl.findDesignerByUsername(designer.getUsername());
			if (optDesigner.isPresent()) {
				return ResponseCodeUtil.UESR_CREATE_EXIST;
			} else {
				PasswordHelper.encryptDesignerPassword(designer);
				designerDaoImpl.createDesigner(designer);
				return ResponseCodeUtil.UESR_OPERATION_SUCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

	@Override
	public int updateDesigner(Designer designer) {
		try {
			designerDaoImpl.updateDesigner(designer);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public int deleteDesigner(int id) {
		try {
			designerDaoImpl.deleteDesigner(id);
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		} catch (Exception e) {
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

	@Override
	public Optional<Designer> getDesignerById(int id) {
		return designerDaoImpl.getDesignerById(id);
	}

	@Override
	public DesignerTableModel getDesignerByPage(int offset, int limit) {
		DesignerTableModel designerTableModel = new DesignerTableModel();
		designerTableModel.setList(designerDaoImpl.getDesignerByPage(offset, limit));
		designerTableModel.setCount(designerDaoImpl.getCountDesigner());
		return designerTableModel;
	}

	@Override
	public DesignerTableModel getDesignerByRealname(String realname, int offset, int limit) {
		DesignerTableModel designerTableModel = new DesignerTableModel();
		designerTableModel.setList(designerDaoImpl.getDesignerByRealname(realname, offset, limit));
		designerTableModel.setCount(designerDaoImpl.getCountDesignerByRealname(realname));
		return designerTableModel;
	}

	@Override
	public Optional<Designer> authorityCheck(String username, String password) {
		return designerDaoImpl.authorityCheck(username, password);
	}

	@Override
	public Optional<Designer> findDesignerByUsername(String username) {
		// TODO Auto-generated method stub
		return designerDaoImpl.findDesignerByUsername(username);
	}

}
