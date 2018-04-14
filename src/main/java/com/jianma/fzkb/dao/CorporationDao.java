package com.jianma.fzkb.dao;

import java.util.List;
import java.util.Optional;

import com.jianma.fzkb.model.Corporation;

public interface CorporationDao {

	public void createCorporation(Corporation corporation);
	
	public void updateCorporation(Corporation corporation);
	
	public void deleteCorporation(int id);
	
	public Optional<Corporation> getCorporationById(int id);
	
	public List<Corporation> getCorporationByPage(int offset, int limit);
	
	public int getCountCorporation();
	
	public List<Corporation> getCorporationByName(String name, int offset, int limit);
	
	public int getCountCorporationByName(String name);
}
