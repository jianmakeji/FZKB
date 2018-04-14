package com.jianma.fzkb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jianma.fzkb.dao.CorporationDao;
import com.jianma.fzkb.model.Corporation;
import com.jianma.fzkb.model.CorporationTableModel;
import com.jianma.fzkb.service.CorporationService;
import com.jianma.fzkb.util.ResponseCodeUtil;

@Repository
@Transactional
@Component
@Qualifier(value = "corporationServiceImpl")
public class CorporationServiceImpl implements CorporationService {

	@Autowired
	@Qualifier("corporationDaoImpl")
	private CorporationDao corporationDaoImpl;
	
	@Override
	public int createCorporation(Corporation corporation) {
		
		try {
			corporationDaoImpl.createCorporation(corporation);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

	@Override
	public int updateCorporation(Corporation corporation) {
		try {
			corporationDaoImpl.updateCorporation(corporation);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

	@Override
	public int deleteCorporation(int id) {
		try {
			corporationDaoImpl.deleteCorporation(id);
			return ResponseCodeUtil.UESR_OPERATION_SUCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseCodeUtil.UESR_OPERATION_FAILURE;
		}
	}

	@Override
	public Optional<Corporation> getCorporationById(int id) {
		return corporationDaoImpl.getCorporationById(id);
	}


	@Override
	public CorporationTableModel getCorporationByPage(int offset, int limit) {
		CorporationTableModel corporationTableModel = new CorporationTableModel();
		corporationTableModel.setList(corporationDaoImpl.getCorporationByPage(offset, limit));
		corporationTableModel.setCount(corporationDaoImpl.getCountCorporation());
		return corporationTableModel;
	}

	@Override
	public CorporationTableModel getCorporationByName(String name, int offset, int limit) {
		CorporationTableModel corporationTableModel = new CorporationTableModel();
		corporationTableModel.setList(corporationDaoImpl.getCorporationByName(name, offset, limit));
		corporationTableModel.setCount(corporationDaoImpl.getCountCorporationByName(name));
		return corporationTableModel;
	}

}
