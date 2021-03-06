package com.jianma.fzkb.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import com.jianma.fzkb.util.Md5SaltTool;
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
				designer.setPassword(Md5SaltTool.getEncryptedPwd(designer.getPassword()));
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
			designer.setPassword(Md5SaltTool.getEncryptedPwd(designer.getPassword()));
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

	@Override
	public int updatePwd(int designerId, String password) {
		try {
			designerDaoImpl.updatePwd(designerId, Md5SaltTool.getEncryptedPwd(password));
			return ResponseCodeUtil.DB_OPERATION_SUCCESS;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ResponseCodeUtil.DB_OPERATION_FAILURE;
		}
	}

}
