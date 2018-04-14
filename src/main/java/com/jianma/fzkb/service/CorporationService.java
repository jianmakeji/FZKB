package com.jianma.fzkb.service;

import java.util.Optional;

import com.jianma.fzkb.model.Corporation;
import com.jianma.fzkb.model.CorporationTableModel;

public interface CorporationService {

	public int createCorporation(Corporation corporation);
	
	public int updateCorporation(Corporation corporation);
	
	public int deleteCorporation(int id);
	
	public Optional<Corporation> getCorporationById(int id);
	
	public CorporationTableModel getCorporationByPage(int offset, int limit);
	
	public CorporationTableModel getCorporationByName(String name, int offset, int limit);
	
}
